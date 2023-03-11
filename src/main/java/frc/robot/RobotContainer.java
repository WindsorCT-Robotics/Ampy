package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.autonomous.AutonomousCommand;
import frc.robot.commands.drive.DriveCommand;
import frc.robot.commands.drive.SetNeutralModeCommand;
import frc.robot.subsystems.*;
import frc.robot.subsystems.IntakeArmsSubsystem.ArmState;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  // Subsystems
  private final ConveyorSubsystem conveyor;
  private final DriveSubsystem drive;
  private final IntakeArmsSubsystem intakeArms;
  private final IntakeRollersSubsystem intakeRollers;
  private final PowerDistributionPanelSubsystem pdp;
  private final LEDSubsystem led;
  // Joysticks
  private final CommandXboxController driveController;

  // A chooser for autonomous commands
  private final SendableChooser<Command> chooser;

  private final double CONVEYOR_SPEED = 0.5;
  private final double INTAKE_ROLLER_SPEED = 0.5;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Initialize subsystems
    conveyor = new ConveyorSubsystem();
    drive = new DriveSubsystem();
    intakeArms = new IntakeArmsSubsystem();
    intakeRollers = new IntakeRollersSubsystem();
    pdp = new PowerDistributionPanelSubsystem(new PowerDistribution());
    led = new LEDSubsystem();

    // Put subsystems on the SmartDashboard
    SmartDashboard.putData(conveyor);
    SmartDashboard.putData(drive);
    SmartDashboard.putData(intakeArms);
    SmartDashboard.putData(intakeRollers);
    SmartDashboard.putData(pdp);

    // Initialize pneumatics
    initializePneumatics();

    // Initialize controllers
    driveController = new CommandXboxController(0);

    // Configure default commands
    drive.setDefaultCommand(
        new DriveCommand(() -> driveController.getLeftY(), () -> driveController.getRightX(), drive));

    // Configure button bindings
    configureButtonBindings();

    // Initialize autonomous chooser
    chooser = new SendableChooser<>();
    chooser.setDefaultOption("Drive forward", new AutonomousCommand(drive));
    SmartDashboard.putData("Auto Mode", chooser);

    // Put commands on the SmartDashboard
    SmartDashboard.putData("AutonomousCommand", new AutonomousCommand(drive));
    SmartDashboard.putData("RaiseIntakeCommand", new MoveIntakeCommand(ArmState.RAISED, intakeArms));
    SmartDashboard.putData("LowerIntakeCommand", new MoveIntakeCommand(ArmState.LOWERED, intakeArms));
    SmartDashboard.putData("ForwardConveyorCommand", new MoveConveyorCommand(CONVEYOR_SPEED, conveyor));
    SmartDashboard.putData("ReverseConveyorCommand", new MoveConveyorCommand(-CONVEYOR_SPEED, conveyor));
    SmartDashboard.putData("ForwardIntakeRollersCommand", new MoveIntakeRollersCommand(INTAKE_ROLLER_SPEED, intakeRollers));
    SmartDashboard.putData("ReverseIntakeRollersCommand", new MoveIntakeRollersCommand(-INTAKE_ROLLER_SPEED, intakeRollers));
  }

  // Used to start compressor
  private void initializePneumatics() {
    try (Compressor pcmCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM)) {
      pcmCompressor.enableDigital();
      System.out.println("Compressor Initialized? " + pcmCompressor.isEnabled());
    }
  }

  /**
   * Configure joysitck button bindings
   */
  private void configureButtonBindings() {
    // intake a game piece
    driveController.a().onTrue(new IntakeCommand(intakeArms, conveyor, intakeRollers));
    driveController.b().whileTrue(new MoveConveyorCommand(0.5, conveyor));
    driveController.x().whileTrue(new SetLedColorCommand(led, 0,0,255));
    driveController.y().whileTrue(new SetLedColorCommand(led, 255, 255, 0));
    driveController.povUp().onTrue(new MoveIntakeCommand(ArmState.RAISED, intakeArms));
    driveController.povDown().onTrue(new MoveIntakeCommand(ArmState.LOWERED, intakeArms));
    // Toggle between brake and coast
    driveController.leftStick()
        .onTrue(
            new ConditionalCommand(
                new SetNeutralModeCommand(NeutralMode.Brake, drive),
                new SetNeutralModeCommand(NeutralMode.Coast, drive),
                () -> (drive.getNeutralMode() == NeutralMode.Coast)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return chooser.getSelected();
  }

}
