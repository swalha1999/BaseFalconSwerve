package frc.robot.subsystems;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
	DigitalInput input = new DigitalInput(9);
	DutyCycleEncoder encoder = new DutyCycleEncoder(input);
	double encoderOffset = 0.9108 ;
	
	private TalonFX leftMotor;
	private TalonFX rightMotor;

	private TalonFXConfiguration swerveAngleFXConfig = new TalonFXConfiguration();
	private final PositionVoltage m_voltagePosition = new PositionVoltage(0);
	double pose = 0;

	public void configMotor() {
		swerveAngleFXConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

		swerveAngleFXConfig.Slot0.kP = 1.2; // An error of 0.5 rotations results in 1.2 volts output
		swerveAngleFXConfig.Slot0.kI = 0.1; 
		swerveAngleFXConfig.Slot0.kD = 0; // A change of 1 rotation per second results in 0.1 volts output
		// Peak output of 8 volts
		swerveAngleFXConfig.Voltage.PeakForwardVoltage = 8;
		swerveAngleFXConfig.Voltage.PeakReverseVoltage = -8;

		swerveAngleFXConfig.Slot1.kP = 40; // An error of 1 rotations results in 40 amps output
		swerveAngleFXConfig.Slot1.kD = 2; // A change of 1 rotation per second results in 2 amps output

		// Peak output of 130 amps
		swerveAngleFXConfig.TorqueCurrent.PeakForwardTorqueCurrent = 130;
		swerveAngleFXConfig.TorqueCurrent.PeakReverseTorqueCurrent = 130;
	}

	public Arm() {
		configMotor();
		rightMotor = new TalonFX(50);
		leftMotor = new TalonFX(8);
		leftMotor.setControl(new Follower(50, true));
		leftMotor.setPosition(0);
		rightMotor.setPosition(0);

		/* Retry config apply up to 5 times, report if failure */
		StatusCode status = StatusCode.StatusCodeNotInitialized;
		for (int i = 0; i < 5; ++i) {

			leftMotor.getConfigurator().apply(swerveAngleFXConfig);
			rightMotor.getConfigurator().apply(swerveAngleFXConfig);

			if (status.isOK())
				break;
		}
		if (!status.isOK()) {
			System.out.println("Could not apply configs, error code: " + status.toString());
		}

		leftMotor.getConfigurator().apply(swerveAngleFXConfig);
		rightMotor.getConfigurator().apply(swerveAngleFXConfig);
	}

	public double getEncoderAngele(){
		return getCorrectedEncoder() * 373 ;   
	}

	public double getCorrectedEncoder(){
		double corrected = encoder.getAbsolutePosition() - this.encoderOffset;
		corrected = corrected * -1;
		return corrected;
	}

	public void setSpeed(double speed) {
		rightMotor.set(speed);
	}

	public void setPose(double pose) {
		this.pose = pose;
		rightMotor.setControl(m_voltagePosition.withPosition(pose));
		}

	public double getPose() {
		return pose;
	}

	public void resetToAbslote(){
		leftMotor.setPosition(getEncoderAngele());
		rightMotor.setPosition(getEncoderAngele());
	}

	public void resetCommandAbslote(){
		pose = getEncoderAngele();
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Arm Right Pose", rightMotor.getPosition().getValue());
		SmartDashboard.putNumber("Arm Left Pose", leftMotor.getPosition().getValue());
		SmartDashboard.putNumber("Commanded arm pose", pose);
		SmartDashboard.putNumber("Arm encoder Angle", getEncoderAngele());
		SmartDashboard.putNumber("Arm encoder Voltage", encoder.getAbsolutePosition());
		SmartDashboard.putNumber("Arm corrected", getCorrectedEncoder());
		
	}

}
