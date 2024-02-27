package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Arm;

public class ShootDown extends SequentialCommandGroup {
    public ShootDown(Intake intake, Shooter shooter, Arm arm, Swerve swerve) {
        addRequirements(intake, shooter);
        addCommands(
                new InstantCommand(() -> shooter.setSpeed(1)),
                new WaitUntilCommand(() -> shooter.getCurrentRPM() > 5000),
                new InstantCommand(() -> intake.setSpeed(1)));
    }

}