package frc.robot.subsystems;

import frc.robot.SwerveModule;
import frc.robot.Constants;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;

import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

public class Swerve extends SubsystemBase {
	public SwerveDrivePoseEstimator vissionPoseEstimator;
	public SwerveDrivePoseEstimator swervePoseEstimator;
	public SwerveModule[] mSwerveMods;
	public Pigeon2 gyro;
	
	DoubleArraySubscriber botPose;
	public NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
	public NetworkTableEntry botpose[] = new NetworkTableEntry[6];
	public NetworkTableEntry vissionTv = table.getEntry("tv");
	

	public Swerve() {

		botPose = table.getDoubleArrayTopic("botpose").subscribe(new double[] {});
		
		gyro = new Pigeon2(Constants.Swerve.pigeonID);
		gyro.getConfigurator().apply(new Pigeon2Configuration());
		gyro.setYaw(0);

		mSwerveMods = new SwerveModule[] {
				new SwerveModule(0, Constants.Swerve.Mod0.constants),
				new SwerveModule(1, Constants.Swerve.Mod1.constants),
				new SwerveModule(2, Constants.Swerve.Mod2.constants),
				new SwerveModule(3, Constants.Swerve.Mod3.constants)
		};

		swervePoseEstimator = new SwerveDrivePoseEstimator(
				Constants.Swerve.swerveKinematics,
				getGyroYaw(),
				getModulePositions(),
				new Pose2d()
		);

		vissionPoseEstimator = new SwerveDrivePoseEstimator(
				Constants.Swerve.swerveKinematics,
				getGyroYaw(),
				getModulePositions(),
				new Pose2d()
		);


		AutoBuilder.configureHolonomic(
            this::getPose, // Robot pose supplier
            this::setPose, // Method to reset odometry (will be called if your auto has a starting pose)
            this::getRobotRelativeSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
            this::driveRobotRelative, // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds
            new HolonomicPathFollowerConfig( // HolonomicPathFollowerConfig, this should likely live in your Constants class
                    new PIDConstants(15.0, 0.0, 0.0), // Translation PID constants
                    new PIDConstants(15.0, 0.0, 0.0), // Rotation PID constants
                    3, // Max module speed, in m/s
                    0.3433, // Drive base radius in meters. Distance from robot center to furthest module.
                    new ReplanningConfig() // Default path replanning config. See the API for the options here
            ),
            () -> {
              // Boolean supplier that controls when the path will be mirrored for the red alliance
              // This will flip the path being followed to the red side of the field.
              // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

              var alliance = DriverStation.getAlliance();
              if (alliance.isPresent()) {
                return alliance.get() == DriverStation.Alliance.Red;
              }
              return false;
            },
            this // Reference to this subsystem to set requirements
    );


	}

	public void driveRobotRelative(ChassisSpeeds robotRelativChassisSpeeds) {
		SwerveModuleState[] swerveModuleStates = Constants.Swerve.swerveKinematics
				.toSwerveModuleStates(robotRelativChassisSpeeds);
			
		SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);
		for (SwerveModule mod : mSwerveMods) {
			mod.setDesiredState(swerveModuleStates[mod.moduleNumber], false);
		}
	}

	public Pose2d getVissionPose(){
		return vissionPoseEstimator.getEstimatedPosition();
	}

	public boolean isAllianceColorRed(){
		var alliance = DriverStation.getAlliance();
		if (alliance.isPresent()) {
			return alliance.get() == DriverStation.Alliance.Red;
		}
		return false;
	}

	public double getDistanceToGoal(){
		boolean isRedAlliance = isAllianceColorRed();
		double x =  getVissionPose().getX() - (isRedAlliance ?  Constants.redGoal.getX() : Constants.blueGoal.getX());
		double y =  getVissionPose().getY() - (isRedAlliance ?  Constants.redGoal.getY() : Constants.blueGoal.getY());
		return Math.sqrt(x*x + y*y);
	}

	public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
		SwerveModuleState[] swerveModuleStates = Constants.Swerve.swerveKinematics.toSwerveModuleStates(
				fieldRelative ? calChassisSpeedsFieldRelative(translation, rotation)
						: calChassisSpeedsRobotRelative(translation, rotation));
		
		SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

		for (SwerveModule mod : mSwerveMods) {
			mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
		}
	}

	public ChassisSpeeds calChassisSpeedsFieldRelative(Translation2d translation, double rotation) {
		return ChassisSpeeds.fromFieldRelativeSpeeds(
				translation.getX(),
				translation.getY(),
				rotation,
				getHeading());
	}
	
	public ChassisSpeeds calChassisSpeedsRobotRelative(Translation2d translation, double rotation) {
		return new ChassisSpeeds(
				translation.getX(),
				translation.getY(),
				rotation);
	}

	/* Used by SwerveControllerCommand in Auto */
	public void setModuleStates(SwerveModuleState[] desiredStates) {
		SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);

		for (SwerveModule mod : mSwerveMods) {
			mod.setDesiredState(desiredStates[mod.moduleNumber], false);
		}
	}

	public SwerveModuleState[] getModuleStates() {
		SwerveModuleState[] states = new SwerveModuleState[4];
		for (SwerveModule mod : mSwerveMods) {
			states[mod.moduleNumber] = mod.getState();
		}
		return states;
	}

	public SwerveModulePosition[] getModulePositions() {
		SwerveModulePosition[] positions = new SwerveModulePosition[4];
		for (SwerveModule mod : mSwerveMods) {
			positions[mod.moduleNumber] = mod.getPosition();
		}
		return positions;
	}

	public ChassisSpeeds getRobotRelativeSpeeds() {
		return Constants.Swerve.swerveKinematics.toChassisSpeeds(
				mSwerveMods[0].getState(),
				mSwerveMods[1].getState(),
				mSwerveMods[2].getState(),
				mSwerveMods[3].getState());
	}

	public Pose2d getPose() {
		return swervePoseEstimator.getEstimatedPosition();
	}

	public void setPose(Pose2d pose) {
		swervePoseEstimator.resetPosition(getGyroYaw(), getModulePositions(), pose);
	}

	public Rotation2d getHeading() {
		return getPose().getRotation();
	}

	public void setHeading(Rotation2d heading) {
		swervePoseEstimator.resetPosition(getGyroYaw(), getModulePositions(), new Pose2d(getPose().getTranslation(), heading));
	}

	public void zeroHeading() {
		swervePoseEstimator.resetPosition(getGyroYaw(), getModulePositions(),
				new Pose2d(getPose().getTranslation(), new Rotation2d()));
	}

	public Rotation2d getGyroYaw() {
		return Rotation2d.fromDegrees(gyro.getYaw().getValue());
	}

	public void resetModulesToAbsolute() {
		for (SwerveModule mod : mSwerveMods) {
			mod.resetToAbsolute();
		}
	}

	@Override
	public void periodic() {
		swervePoseEstimator.update(getGyroYaw(), getModulePositions());
		vissionPoseEstimator.update(getGyroYaw(), getModulePositions());
		if(vissionTv.getDouble(0) == 1.0){
			double[] VissionLocation = botPose.get();
			Pose2d VissionRobotPose2d =  new Pose2d(
					VissionLocation[0] + Constants.fieldMiddle.getX(),
					VissionLocation[1] + Constants.fieldMiddle.getY(),
					getHeading() // or you can use the camera angle new Rotation2d(VissionLocation[5])
			);
			double VissionRobotlatnsy = Timer.getFPGATimestamp() - (VissionLocation[6]/1000.0);
		
			vissionPoseEstimator.addVisionMeasurement(VissionRobotPose2d,VissionRobotlatnsy);
		}

		// enable this to see the robot pose on the dashboard
		// for (SwerveModule mod : mSwerveMods) {
		//     SmartDashboard.putNumber("Mod " + mod.moduleNumber + " CANcoder", mod.getCANcoder().getDegrees());
		//     SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Angle", mod.getPosition().angle.getDegrees());
		//     SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);
		// }

		SmartDashboard.putNumber("OdoX", getPose().getX());
		SmartDashboard.putNumber("OdoY", getPose().getY());
		SmartDashboard.putNumber("OdoRot", getPose().getRotation().getDegrees());
		SmartDashboard.putNumber("Vision x", vissionPoseEstimator.getEstimatedPosition().getX());
		SmartDashboard.putNumber("Vision y", vissionPoseEstimator.getEstimatedPosition().getY());
		

		SmartDashboard.putNumber("DistanceToGoal", getDistanceToGoal());
	}
}