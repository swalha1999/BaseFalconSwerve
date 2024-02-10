package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import java.util.function.DoubleSupplier;


public class IntakeCommand extends Command{
    
    private Intake intake;
    private DoubleSupplier upSpeed;
    private DoubleSupplier downSpeed;

    private DoubleSupplier suckerSpeed;

    public IntakeCommand(Intake Intake, DoubleSupplier up, DoubleSupplier down, DoubleSupplier suckerSpeed) {
        addRequirements(Intake);
        this.intake = Intake;
        this.upSpeed = up;
        this.downSpeed = down;
        this.suckerSpeed = suckerSpeed;
        
    }
    
    @Override
    public void execute() {
        
        if(upSpeed.getAsDouble() > 0.1 || downSpeed.getAsDouble() > 0.1){
            intake.manualControlAngle(upSpeed.getAsDouble() - downSpeed.getAsDouble());
        }

        if(suckerSpeed.getAsDouble() > 0.05 || suckerSpeed.getAsDouble() < -0.05 ){
            intake.manualControlSpeed(suckerSpeed.getAsDouble() );
        }
    }
}
