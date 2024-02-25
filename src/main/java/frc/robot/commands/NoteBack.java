package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class NoteBack extends SequentialCommandGroup {
    public NoteBack(Intake intake, Shooter shooter) {
        addRequirements(intake, shooter);
        addCommands(
                new InstantCommand(() -> shooter.setSpeed(-0.3)),
                new InstantCommand(() -> intake.setSpeed(-0.1)));
    }

}