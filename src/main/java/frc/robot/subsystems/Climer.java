package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climer extends SubsystemBase {
    private CANSparkFlex climerMotor;
	

    public Climer() {
        climerMotor = new CANSparkFlex(59, CANSparkFlex.MotorType.kBrushless);
        climerMotor.setIdleMode(IdleMode.kBrake);
    }

    public void setSpeed(double speed) {
        climerMotor.set(speed * 0.8);

    }

}
