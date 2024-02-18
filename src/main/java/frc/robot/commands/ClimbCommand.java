package frc.robot.commands;

import frc.robot.subsystems.Climer;

import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj2.command.Command;

public class ClimbCommand extends Command {
    private BooleanSupplier up;
    private BooleanSupplier down;
    private Climer climer;

    public ClimbCommand(Climer climer, BooleanSupplier up, BooleanSupplier down) {
        addRequirements(climer);
        this.climer= climer;

        this.up = up;
        this.down = down;

    }

    @Override
    public void execute() {
        /* Get Values, Deadband */
        double speed = up.getAsBoolean() ? 1 : down.getAsBoolean() ? -1 : 0;
        climer.setSpeed(speed);
        
    }
}