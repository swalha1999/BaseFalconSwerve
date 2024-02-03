package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class StopShootCommand extends SequentialCommandGroup {
    
    public StopShootCommand(Shooter shooter, Intake intake) {
        addRequirements(shooter, intake);
        addCommands(
            new InstantCommand(() -> shooter.setSpeed(0)),
            new InstantCommand(() -> intake.manualControlSpeed(0))
        );        
    }

    
}
