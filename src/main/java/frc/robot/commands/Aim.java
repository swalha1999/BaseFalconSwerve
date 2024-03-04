package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Arm;

public class Aim extends Command {

    private Swerve swerve;
    private Arm arm;

    public Aim(Arm arm, Swerve swerve) {
        addRequirements(arm);
        this.arm = arm;
        this.swerve = swerve;
    }

    @Override
    public void execute() {
        if (swerve.getDistanceToGoal() > 2.5) {
            arm.setPose((-3.41) * (swerve.getDistanceToGoal())*(swerve.getDistanceToGoal()) +28.71 * (swerve.getDistanceToGoal())-24);
        }
        else {
            arm.setPose((-3.41) * (swerve.getDistanceToGoal())*(swerve.getDistanceToGoal()) +28.71 * (swerve.getDistanceToGoal())-21);
        }
        
    }
}
