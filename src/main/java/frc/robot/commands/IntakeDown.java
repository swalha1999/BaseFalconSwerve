package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm;

public class IntakeDown extends SequentialCommandGroup {
    public IntakeDown(Intake intake, Arm arm) {
        addRequirements(intake, arm);
        addCommands(
            new InstantCommand(() -> arm.setPose(0)),
            new InstantCommand(() -> intake.setSpeed(0.5)));
    }

}