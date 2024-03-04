package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Shoot extends SequentialCommandGroup {
    public Shoot(Intake intake, Shooter shooter) {
        addRequirements(intake, shooter);
        addCommands(
                new InstantCommand(() -> shooter.setSpeed(1)),
                new WaitUntilCommand(() -> shooter.getCurrentRPM() > 4600),
                new InstantCommand(() -> intake.setSpeed(1)));
    }

}