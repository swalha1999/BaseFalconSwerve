package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;


public class Intake extends SubsystemBase {
	private CANSparkFlex intakeMotor;
	private final I2C.Port i2cPort = I2C.Port.kOnboard;

	private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

	public Intake() {
		intakeMotor = new CANSparkFlex(60, CANSparkFlex.MotorType.kBrushless);
		intakeMotor.setInverted(true);
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

	@Override
	public void periodic() {
	
		Color detectedColor = m_colorSensor.getColor();

		double IR = m_colorSensor.getIR();
		
		SmartDashboard.putNumber("Red", detectedColor.red);
		SmartDashboard.putNumber("Green", detectedColor.green);
		SmartDashboard.putNumber("Blue", detectedColor.blue);
		SmartDashboard.putNumber("IR", IR);
		
		int proximity = m_colorSensor.getProximity();
		
		SmartDashboard.putNumber("Proximity", proximity);

	}

}
