package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class StopShoot extends SequentialCommandGroup {
    public StopShoot(Intake intake, Shooter shooter, Arm arm) {
        addRequirements(intake, shooter);
        addCommands(
                new InstantCommand(() -> shooter.setSpeed(0)),
                new InstantCommand(() -> arm.resetToAbslote()),
                new InstantCommand(() -> intake.setSpeed(0)));
    }

}