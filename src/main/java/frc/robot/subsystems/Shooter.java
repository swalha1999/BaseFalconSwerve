package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkFlex;

public class Shooter extends SubsystemBase {
    
    private CANSparkFlex mShooterMotorLeft;
    private CANSparkFlex mShooterMotorRight;

    public Shooter() {
        mShooterMotorLeft = new CANSparkFlex(51, CANSparkFlex.MotorType.kBrushless);
        mShooterMotorRight = new CANSparkFlex(52, CANSparkFlex.MotorType.kBrushless);
    }

    public void setSpeed(double speed) {
        mShooterMotorLeft.set(speed);
        mShooterMotorRight.set(speed*-0.7);
    }
    
}
