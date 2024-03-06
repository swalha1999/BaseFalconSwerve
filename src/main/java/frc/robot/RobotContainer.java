package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

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
    private final JoystickButton climerUp = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton climerDown = new JoystickButton(driver, XboxController.Button.kB.value);

    /*Operator Buttons*/
    private final JoystickButton intakeDown = new JoystickButton(operator, XboxController.Button.kA.value);
    private final JoystickButton shoot = new JoystickButton(operator, XboxController.Button.kY.value);
    private final JoystickButton shootDown = new JoystickButton(operator, XboxController.Button.kB.value);
    private final JoystickButton backNote = new JoystickButton(operator, XboxController.Button.kX.value);

    /* Subsystems */
    public final Swerve s_Swerve; 
    public final Arm s_Arm; 
    public final Intake s_Inatke;
    public final Shooter s_Shooter;
    public final Climer s_Climer;

    private final SendableChooser<Command> autoChooser;

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve = new Swerve();
        s_Arm = new Arm();
        s_Inatke = new Intake();
        s_Shooter = new Shooter();
        s_Climer = new Climer();
        
        NamedCommands.registerCommand("ntoe back", new NoteBack(s_Inatke, s_Shooter));
        NamedCommands.registerCommand("pre shoot", new PreShoot(s_Shooter));
        NamedCommands.registerCommand("shoot up", new AimAndShoot(s_Inatke, s_Shooter, s_Arm, s_Swerve));
        NamedCommands.registerCommand("stop shoot", new StopShoot(s_Inatke, s_Shooter, s_Arm));
        NamedCommands.registerCommand("intake down", new IntakeDown(s_Inatke, s_Arm));
        NamedCommands.registerCommand("intake up", new IntakeUp(s_Inatke, s_Arm));
        NamedCommands.registerCommand("aim shooter", new Aim(s_Arm, s_Swerve));     
        
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis),
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );
        
        s_Arm.setDefaultCommand(
            new ArmCommand(
                s_Arm,
                () -> operator.getRawAxis(XboxController.Axis.kRightTrigger.value),
                () -> operator.getRawAxis(XboxController.Axis.kLeftTrigger.value)
            )
        );

        s_Climer.setDefaultCommand(
            new ClimbCommand(
                s_Climer,
                () -> climerUp.getAsBoolean(),
                () -> climerDown.getAsBoolean()
            )
        );

        configureButtonBindings();

        // in the auto shooter we can set a defult auto in 
         // A chooser for autonomous commands
        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Chooser", autoChooser);
    }

    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading()));
        
        intakeDown
            .onTrue(new IntakeDown(s_Inatke, s_Arm))
            .onFalse(new IntakeUp(s_Inatke, s_Arm));
        
        shoot
            .onTrue(new AimAndShoot(s_Inatke, s_Shooter, s_Arm, s_Swerve))
            .onFalse(new StopShoot(s_Inatke, s_Shooter, s_Arm));

        shootDown
            .onTrue(new ShootDown(s_Inatke, s_Shooter, s_Arm, s_Swerve))
            .onFalse(new StopShoot(s_Inatke, s_Shooter, s_Arm));
        
        backNote
            .onTrue(new NoteBack(s_Inatke, s_Shooter))
            .onFalse(new StopShoot(s_Inatke, s_Shooter, s_Arm));
    }
    
    public Command getAutonomousCommand() {
        // return new exampleAuto(s_Swerve);
        return autoChooser.getSelected();
    }
}    
