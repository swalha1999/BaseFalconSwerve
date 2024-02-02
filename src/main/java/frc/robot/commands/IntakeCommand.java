package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import java.util.function.DoubleSupplier;


public class IntakeCommand extends Command{
    
    private Intake Intake;
    private DoubleSupplier upSpeed;
    private DoubleSupplier downSpeed;

    private DoubleSupplier suckerSpeed;

    public IntakeCommand(Intake Intake, DoubleSupplier up, DoubleSupplier down, DoubleSupplier suckerSpeed) {
        this.Intake = Intake;
        addRequirements(Intake);

        this.upSpeed = up;
        this.downSpeed = down;
        this.suckerSpeed = suckerSpeed;
        
    }
    
    @Override
    public void execute() {
        Intake.manualControlAngle(upSpeed.getAsDouble() - downSpeed.getAsDouble());
        Intake.manualControlSpeed(suckerSpeed.getAsDouble());
    }
}
