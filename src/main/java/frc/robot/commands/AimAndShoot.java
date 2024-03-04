package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;

public class AimAndShoot extends ParallelCommandGroup {
    public AimAndShoot(Intake intake, Shooter shooter, Arm arm, Swerve swerve) {
        addCommands(
                new Aim(arm, swerve),
                new Shoot(intake, shooter));
    }
}
