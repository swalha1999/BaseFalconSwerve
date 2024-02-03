package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Intake;

public class IntakeUpCommand extends SequentialCommandGroup {
    public IntakeUpCommand(Intake intake) {
        addCommands(
            new InstantCommand(() -> intake.setPose(49)),
            new InstantCommand(() -> intake.manualControlSpeed(0)),
            
            new WaitUntilCommand(() -> intake.getCurrentAngle() > 35 ),
            new InstantCommand(() -> intake.manualControlSpeed(-0.8)),
            new WaitCommand(0.1),
            new InstantCommand(() -> intake.manualControlSpeed(0)),
            new WaitCommand(0.1),
            new InstantCommand(() -> intake.manualControlSpeed(-0.8)),
            new WaitCommand(0.1),
            new InstantCommand(() -> intake.manualControlSpeed(0))
        );
    }
}
