
package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {

  private static RobotContainer robotContainer = new RobotContainer();

  // The robot's subsystems
  public final DriveSubsystem drive = new DriveSubsystem();
  public final boolean isPrecisionOn = false;

  // PDP
  private final PowerDistributionPanelSubsystem PDP;
  // Joysticks
  private final CommandXboxController operatorController = new CommandXboxController(1);
  private final CommandXboxController driveController = new CommandXboxController(0);

  // A chooser for autonomous commands
  SendableChooser<Command> chooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  private RobotContainer() {

    // Smartdashboard Subsystems

    initializePneumatics();

    SmartDashboard.putData(IntakeArmsSubsystem.getInstance());

    SmartDashboard.putData(drive);

    // SmartDashboard Buttons
    SmartDashboard.putData("AutonomousCommand", new AutonomousCommand());
    SmartDashboard.putData("RaiseIntakeCommand", new RaiseIntakeCommand(IntakeArmsSubsystem.getInstance()));
    SmartDashboard.putData("LowerIntakeCommand", new LowerIntakeCommand(IntakeArmsSubsystem.getInstance()));
    SmartDashboard.putData("ForwardConveyorCommand", new ForwardConveyorCommand());
    SmartDashboard.putData("ReverseConveyorCommand", new ReverseConveyorCommand());
    // SmartDashboard.putData("ForwardIntakeRollersCommand", new
    // ForwardIntakeRollersCommand());
    // SmartDashboard.putData("ReverseIntakeRollersCommand", new
    // ReverseIntakeRollersCommand());
    // SmartDashboard.putData("IntakeCommand", new IntakeCommand(intakeRollers));

    PDP = new PowerDistributionPanelSubsystem(new PowerDistribution());

    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands

    drive.setDefaultCommand(
        new DriveCommand(() -> driveController.getLeftY(), () -> driveController.getRightX(), drive));

    // Configure autonomous sendable chooser

    SmartDashboard.putData("Auto Mode", chooser);
  }

  // Used to start compressor
  private void initializePneumatics() {
    try (Compressor pcmCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM)) {
      pcmCompressor.enableDigital();
      System.out.println("Compressor Initialized? " + pcmCompressor.isEnabled());
    }
  }

  public static RobotContainer getInstance() {
    return robotContainer;
  }

  private void configureButtonBindings() {
    operatorController.povUp().onTrue(new IntakeCommand(IntakeArmsSubsystem.getInstance()));
    operatorController.povDown().onTrue(new EjectCommand(IntakeArmsSubsystem.getInstance()));
    operatorController.y().onTrue(new RaiseIntakeCommand(IntakeArmsSubsystem.getInstance()));
    operatorController.a().onTrue(new LowerIntakeCommand(IntakeArmsSubsystem.getInstance()));
  }

  public CommandXboxController getDriveController() {
    return driveController;
  }

  public CommandXboxController getOperatorController() {
    return operatorController;
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
