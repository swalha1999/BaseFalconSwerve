package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase {
    private CANSparkFlex angleMotor;
    private SparkPIDController anglePidController;
    private RelativeEncoder angleEncoder;
    private VictorSP  sucker;
    private double pose;

    public Intake() {
        // PWM motor
        sucker = new VictorSP(0);
        
        // REV Spark Motor
        angleMotor = new CANSparkFlex(53, CANSparkFlex.MotorType.kBrushless);
        anglePidController = angleMotor.getPIDController();
        angleEncoder = angleMotor.getEncoder();

        angleEncoder.setPosition(0);

        
        anglePidController.setP(0.01);
        anglePidController.setI(0);
        anglePidController.setD(0);
        anglePidController.setIZone(0);
        anglePidController.setFF(0);
        anglePidController.setOutputRange(-0.2, 0.2);

        pose = 0;


    }

    public void manualControlAngle(double current) {
        if (abs(current) > 0.2){
            anglePidController.setReference(current,ControlType.kDutyCycle);
            angleMotor.set(current);
            pose = angleEncoder.getPosition();
        }
    }

    public void setPose(double pose){
        this.pose = pose;
    }

    public double getPose(){
        return pose;
    }

    public double getCurrentAngle(){
        return angleEncoder.getPosition();
    }

    public void manualControlSpeed(double speed){
        sucker.set(speed);
    }
    
    private double abs(double num){
        return num > 0 ? num : -num;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Intake angle", angleEncoder.getPosition());
        anglePidController.setReference(pose, ControlType.kPosition);
        
    }

    

}
