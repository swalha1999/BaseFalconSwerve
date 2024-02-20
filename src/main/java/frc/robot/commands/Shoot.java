package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Arm;

public class Shoot extends SequentialCommandGroup {
    public Shoot(Intake intake, Shooter shooter, Arm arm) {
        addRequirements(intake, shooter);
        addCommands(
                new InstantCommand(() -> shooter.setSpeed(1)),
                new WaitCommand(2),
                new InstantCommand(() -> intake.setSpeed(1)));
    }

}