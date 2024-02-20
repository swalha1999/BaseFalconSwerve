package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

import java.util.function.DoubleSupplier;

public class ArmCommand extends Command {

    private DoubleSupplier upSpeed;
    private DoubleSupplier downSpeed;
    private Arm arm;

    public ArmCommand(Arm arm, DoubleSupplier up, DoubleSupplier down) {
        addRequirements(arm);
        this.upSpeed = up;
        this.downSpeed = down;
        this.arm = arm;
    }

    @Override
    public void execute() {
        double speed = upSpeed.getAsDouble() - downSpeed.getAsDouble();
        double pose = arm.getPose() + (speed * 1);
        if (pose < 0) {
            pose = 0;
        }
        if (pose > 90) {
            pose = 90;
        }
        arm.setPose(pose);
    }
}
