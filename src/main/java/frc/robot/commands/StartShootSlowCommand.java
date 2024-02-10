package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class StartShootSlowCommand extends SequentialCommandGroup {
    
    public StartShootSlowCommand(Shooter shooter, Intake intake) {
        addRequirements(shooter, intake);
        addCommands(
            new InstantCommand(() -> shooter.setSpeed(0.3)),
            new WaitCommand(1),
            new InstantCommand(() -> intake.manualControlSpeed(1))
        );                
    }
    
}
