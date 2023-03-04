
package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.autonomous.AutonomousCommand;
import frc.robot.commands.drive.DriveCommand;
import frc.robot.commands.drive.SetNeutralModeCommand;
import frc.robot.subsystems.*;
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

  private static RobotContainer robotContainer = new RobotContainer();

  // The robot's subsystems
  public final DriveSubsystem drive;
  public final boolean isPrecisionOn = false;

  // Joysticks
  private final CommandXboxController operatorController = new CommandXboxController(1);
  private final CommandXboxController driveController = new CommandXboxController(0);

  // A chooser for autonomous commands
  SendableChooser<Command> chooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  private RobotContainer() {
    drive = new DriveSubsystem();

    // Smartdashboard Subsystems

    initializePneumatics();

    SmartDashboard.putData(IntakeArmsSubsystem.getInstance());

    SmartDashboard.putData(drive);

    // SmartDashboard Buttons
    SmartDashboard.putData("AutonomousCommand", new AutonomousCommand(drive));
    SmartDashboard.putData("RaiseIntakeCommand", new RaiseIntakeCommand(IntakeArmsSubsystem.getInstance()));
    SmartDashboard.putData("LowerIntakeCommand", new LowerIntakeCommand(IntakeArmsSubsystem.getInstance()));
    SmartDashboard.putData("ForwardConveyorCommand", new ForwardConveyorCommand());
    SmartDashboard.putData("ReverseConveyorCommand", new ReverseConveyorCommand());
    SmartDashboard.putData("ForwardIntakeRollersCommand", new ForwardIntakeRollersCommand());
    SmartDashboard.putData("ReverseIntakeRollersCommand", new ReverseIntakeRollersCommand());
    SmartDashboard.putData("IntakeCommand", new IntakeCommand(IntakeArmsSubsystem.getInstance()));

    new PowerDistributionPanelSubsystem(new PowerDistribution());

    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands

    drive.setDefaultCommand(
        new DriveCommand(() -> driveController.getLeftY(), () -> driveController.getRightX(), drive));

    // Configure autonomous sendable chooser
    chooser.setDefaultOption("Drive forward", new AutonomousCommand(drive));
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
    // Toggle between brake and coast
    driveController.a()
        .onTrue(
            new ConditionalCommand(
                new SetNeutralModeCommand(NeutralMode.Brake, drive),
                new SetNeutralModeCommand(NeutralMode.Coast, drive),
                () -> (drive.getNeutralMode() == NeutralMode.Coast)));
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
