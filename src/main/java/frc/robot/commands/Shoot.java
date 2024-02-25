package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Arm;

public class Shoot extends SequentialCommandGroup {
    public Shoot(Intake intake, Shooter shooter, Arm arm, Swerve swerve) {
        addRequirements(intake, shooter);
        addCommands(
                new InstantCommand(() -> arm.setPose((-2.40048) * (swerve.getDistanceToGoal())*(swerve.getDistanceToGoal()) +23.1937 * (swerve.getDistanceToGoal())-19.2118)),
                new InstantCommand(() -> shooter.setSpeed(1)),
                new WaitCommand(0.5),
                new InstantCommand(() -> intake.setSpeed(1)));
    }

}