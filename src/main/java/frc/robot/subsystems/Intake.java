package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase {
    private CANSparkFlex angleMotor;
    private CANSparkMax sucker;

    public Intake() {
        angleMotor = new CANSparkFlex(53, CANSparkFlex.MotorType.kBrushless);
        sucker = new CANSparkMax(54, CANSparkMax.MotorType.kBrushless);
    }

    public void manualControlAngle(double current) {
        angleMotor.set(current * 0.1);
    }

    public void manualControlSpeed(double speed){
        sucker.set(speed);
    }
    
}
