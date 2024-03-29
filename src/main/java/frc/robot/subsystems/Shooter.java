package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkBase.IdleMode;

public class Shooter extends SubsystemBase {
    private CANSparkFlex upperMotor;
    private CANSparkFlex lowerMotor;

    public Shooter() {
        upperMotor = new CANSparkFlex(61, CANSparkFlex.MotorType.kBrushless);
        lowerMotor = new CANSparkFlex(62, CANSparkFlex.MotorType.kBrushless);
        
        upperMotor.setInverted(true);
        lowerMotor.setInverted(true);
        
        upperMotor.setIdleMode(IdleMode.kCoast);
        lowerMotor.setIdleMode(IdleMode.kCoast);
    }

    public double getCurrentRPM(){
        return (upperMotor.getEncoder().getVelocity() +  lowerMotor.getEncoder().getVelocity()) / 2;
    }

    public void setSpeed(double speed) {
        upperMotor.set(speed);
        lowerMotor.set(speed);
    }

    @Override
	public void periodic() {
		SmartDashboard.putNumber("Shooter RPM", getCurrentRPM());
	}
}

