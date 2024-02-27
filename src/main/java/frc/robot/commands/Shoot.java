package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Arm;

public class Shoot extends SequentialCommandGroup {
    public Shoot(Intake intake, Shooter shooter, Arm arm, Swerve swerve) {
        addRequirements(intake, shooter);
        addCommands(
                new InstantCommand(() -> arm.setPose((-3.41) * (swerve.getDistanceToGoal())*(swerve.getDistanceToGoal()) +28.71 * (swerve.getDistanceToGoal())-22.49)),
                new InstantCommand(() -> shooter.setSpeed(1)),
                new InstantCommand(() -> arm.resetToAbslote()),
                new WaitUntilCommand(() -> shooter.getCurrentRPM() > 5000),
                new InstantCommand(() -> intake.setSpeed(1)));
    }

}