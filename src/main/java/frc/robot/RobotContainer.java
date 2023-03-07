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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

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

  // Joysticks
  private final CommandXboxController driveController;

  // A chooser for autonomous commands
  private final SendableChooser<Command> chooser;

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
    SmartDashboard.putData("ForwardConveyorCommand", new MoveConveyorCommand(0.3, conveyor));
    SmartDashboard.putData("ReverseConveyorCommand", new MoveConveyorCommand(-0.3, conveyor));
    SmartDashboard.putData("ForwardIntakeRollersCommand", new ForwardIntakeRollersCommand(intakeRollers));
    SmartDashboard.putData("ReverseIntakeRollersCommand", new ReverseIntakeRollersCommand(intakeRollers));
    SmartDashboard.putData("IntakeCommand", new IntakeCommand(intakeArms, conveyor, intakeRollers));
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
    driveController.a()
        .onTrue(
            new SequentialCommandGroup(
                new MoveIntakeCommand(ArmState.LOWERED, intakeArms),
                new ParallelCommandGroup(
                    new MoveConveyorCommand(0.5, conveyor),
                    new ForwardIntakeRollersCommand(intakeRollers)).until(() -> !conveyor.isEmpty()),
                new MoveIntakeCommand(ArmState.RAISED, intakeArms)));
    driveController.b().whileTrue(new MoveConveyorCommand(0.5, conveyor));
    driveController.y().whileTrue(new EjectCommand(intakeArms, conveyor, intakeRollers));
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
