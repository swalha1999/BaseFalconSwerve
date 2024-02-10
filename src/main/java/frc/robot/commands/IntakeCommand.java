package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;


public class IntakeCommand extends Command{
    
    private boolean alreadyDoneSucker = true;
    private boolean alreadyDoneAngle = true;
    private Intake intake;
    private DoubleSupplier upSpeed;
    private DoubleSupplier downSpeed;
    private BooleanSupplier suck;
    private BooleanSupplier spit;

    public IntakeCommand(Intake Intake, DoubleSupplier up, DoubleSupplier down, BooleanSupplier suck, BooleanSupplier spit) {
        addRequirements(Intake);
        this.intake = Intake;
        this.upSpeed = up;
        this.downSpeed = down;
        this.suck = suck;
        this.spit = spit;
        
    }
    
    @Override
    public void execute() {
        
        if(upSpeed.getAsDouble() > 0.1 || downSpeed.getAsDouble() > 0.1){
            intake.manualControlAngle(upSpeed.getAsDouble() - downSpeed.getAsDouble());
            alreadyDoneAngle = false;
        }
        else if(!alreadyDoneAngle){
            intake.manualControlAngle(0);
            alreadyDoneAngle = true;
        }


        if(suck.getAsBoolean() || spit.getAsBoolean()){
            intake.manualControlSpeed(suck.getAsBoolean() ? 1 : -1);
            alreadyDoneSucker = false;
        }
        else if(!alreadyDoneSucker){
            intake.manualControlSpeed(0);
            alreadyDoneSucker = true;
        }
    }
}
