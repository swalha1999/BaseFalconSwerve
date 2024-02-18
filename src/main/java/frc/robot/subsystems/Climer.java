package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climer extends SubsystemBase {
    private VictorSP rightMotor;

    public Climer() {
        rightMotor = new VictorSP(0);
    }

    public void setSpeed(double speed) {
        rightMotor.set(speed);

    }

}
