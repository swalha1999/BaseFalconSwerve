package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class ShooterCommad extends Command{
    
    private Shooter Shooter;
    private DoubleSupplier speedSup;

    public ShooterCommad(Shooter Shooter, DoubleSupplier speedSup) {
        this.Shooter = Shooter;
        addRequirements(Shooter);

        this.speedSup = speedSup;
    }
    
    @Override
    public void execute() {
        double speed = speedSup.getAsDouble();
        Shooter.setSpeed(speed);
    }
    
}
