package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Intake;

public class IntakeDownCommand extends SequentialCommandGroup {
    public IntakeDownCommand(Intake intake) {
        addRequirements(intake);
        addCommands(
            new InstantCommand(() -> intake.setPose(0)),
            new WaitUntilCommand(() -> intake.getCurrentAngle() < 3),
            new InstantCommand(() -> intake.manualControlSpeed(-0.8))
        );
    }
    
}
