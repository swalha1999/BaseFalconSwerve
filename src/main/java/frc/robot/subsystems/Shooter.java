package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkFlex;

public class Shooter extends SubsystemBase {
    private CANSparkFlex leftMotor;
    private CANSparkFlex rightMotor;

    public Shooter() {
        leftMotor = new CANSparkFlex(51, CANSparkFlex.MotorType.kBrushless);
        rightMotor = new CANSparkFlex(52, CANSparkFlex.MotorType.kBrushless);
    }

    public void setSpeed(double speed) {
        leftMotor.set(speed);
        rightMotor.set(speed * -0.8);
    }

    

}
