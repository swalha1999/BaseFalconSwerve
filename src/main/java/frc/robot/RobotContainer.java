package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.*;
import frc.robot.subsystems.*;

public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(0);
    private final XboxController operator = new XboxController(1);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

    private final JoystickButton intakeGroundButton = new JoystickButton(operator, XboxController.Button.kA.value);
    private final JoystickButton middleShootButton = new JoystickButton(operator, XboxController.Button.kY.value);
    private final JoystickButton shootButton = new JoystickButton(operator,XboxController.Button.kRightBumper.value);
    private final JoystickButton intakeInButton = new JoystickButton(operator, XboxController.Button.kB.value);
    private final JoystickButton intakeOutButton = new JoystickButton(operator, XboxController.Button.kX.value);

    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();
    private final Intake s_Intake = new Intake();
    private final Shooter s_Shooter = new Shooter();

    private final SendableChooser<Command> autoChooser;

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis),
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );

        s_Intake.setDefaultCommand(
            new IntakeCommand(
            s_Intake,
            () -> operator.getRawAxis(XboxController.Axis.kLeftTrigger.value),
            () -> operator.getRawAxis(XboxController.Axis.kRightTrigger.value),
            () -> intakeInButton.getAsBoolean(),
            () -> intakeOutButton.getAsBoolean()
        ));

        configureButtonBindings();

        // in the auto shooter we can set a defult auto in 
        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Chooser", autoChooser);
    }

    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading()));
        
        intakeGroundButton
            .onTrue(new IntakeDownCommand(s_Intake))
            .onFalse(new IntakeUpCommand(s_Intake));
            
        shootButton
            .onTrue(new StartShootCommand(s_Shooter, s_Intake))
            .onFalse(new StopShootCommand(s_Shooter, s_Intake));
        
        middleShootButton
            .onTrue(new StartShootSlowCommand(s_Shooter, s_Intake))
            .onFalse(new StopShootCommand(s_Shooter, s_Intake));



    }
    
    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}
