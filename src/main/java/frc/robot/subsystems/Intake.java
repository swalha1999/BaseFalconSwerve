package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
// import com.revrobotics.Rev2mDistanceSensor;
// import com.revrobotics.Rev2mDistanceSensor.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Intake extends SubsystemBase {
	private CANSparkFlex intakeMotor;
	// public Rev2mDistanceSensor distanceSensor;
	
	public Intake() {
		intakeMotor = new CANSparkFlex(60, CANSparkFlex.MotorType.kBrushless);
		intakeMotor.setInverted(true);
		// distanceSensor = new Rev2mDistanceSensor(Port.kOnboard);
		// distanceSensor.setMeasurementPeriod(0);
		// distanceSensor.setAutomaticMode(true);
	}

	public void setSpeed(double speed) {
		intakeMotor.set(speed);
	}

	public double getSpeed() {
		return intakeMotor.get();
	}

	public void stop() {
		intakeMotor.set(0);
	}

	// public boolean isNoteInside(){
	// 	return distanceSensor.getRange() < 15  && distanceSensor.isRangeValid(); 
	// }

	@Override
	public void periodic() {
			// distanceSensor.setAutomaticMode(true);
			// SmartDashboard.putBoolean("Range Valid", distanceSensor.isRangeValid());
			// SmartDashboard.putNumber("Range Onboard", distanceSensor.getRange());
			// SmartDashboard.putNumber("Timestamp Onboard", distanceSensor.getTimestamp());
	}

}
