package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase {
    private CANSparkFlex intakeMotor;

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

}
