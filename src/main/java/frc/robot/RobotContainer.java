package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.autonomous.AutoScoreCommand;
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
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  // Subsystems
  private final ConveyorSubsystem conveyor;
  private final DriveSubsystem drive;
  private final IntakeArmsSubsystem intakeArms;
  private final IntakeRollersSubsystem intakeRollers;
  private final PowerDistributionPanelSubsystem pdp;
  private final LEDSubsystem ledSubsystem;
  // Joysticks
  private final CommandXboxController driveController;

  // A chooser for autonomous commands
  private final SendableChooser<Command> chooser;

  private final double CONVEYOR_SPEED = 0.8;
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
    ledSubsystem = new LEDSubsystem();

    // Initialize pneumatics
    initializePneumatics();

    // Initialize controllers
    driveController = new CommandXboxController(0);

    // Configure default commands
    drive.setDefaultCommand(
        new DriveCommand(() -> driveController.getLeftY(), () -> driveController.getRightX(), drive));
    conveyor.setDefaultCommand(new MoveConveyorCommand(-0.1, conveyor));
    ledSubsystem.setDefaultCommand(new SetLedColorCommand(ledSubsystem, 255, 127, 0));

    // Configure button bindings
    configureButtonBindings();

    // Initialize autonomous chooser
    chooser = new SendableChooser<>();
    chooser.addOption("Score left", new AutoScoreCommand(conveyor, drive, 5));
    chooser.addOption("Score right", new AutoScoreCommand(conveyor, drive, 8));
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

  /**
   * Configure joysitck button bindings
   */
  private void configureButtonBindings() {
    // set color of LEDs
    driveController.x().whileTrue(new SetLedColorCommand(ledSubsystem, 0, 0, 255));
    driveController.y().whileTrue(new SetLedColorCommand(ledSubsystem, 255, 102, 0));
    // Toggle between brake and coast
    driveController.b()
        .onTrue(
            new ConditionalCommand(
                new SetNeutralModeCommand(NeutralMode.Brake, drive),
                new SetNeutralModeCommand(NeutralMode.Coast, drive),
                () -> (drive.getNeutralMode() == NeutralMode.Coast)));

    driveController.leftBumper().onTrue(new IntakeFromFloorCommand(intakeArms, conveyor, intakeRollers));
    driveController.rightBumper().onTrue(new IntakeFromSubstationCommand(intakeArms, conveyor, intakeRollers));

    driveController.a().onTrue(new EjectCommand(intakeArms, conveyor, intakeRollers));

    Trigger leftTrigger = new Trigger(() -> driveController.getLeftTriggerAxis() > 0.1);
    leftTrigger.whileTrue(
        new MoveIntakeRollersCommand(-INTAKE_ROLLER_SPEED * driveController.getLeftTriggerAxis(), intakeRollers));
    Trigger rightTrigger = new Trigger(() -> driveController.getRightTriggerAxis() > 0.3);
    rightTrigger.whileTrue(new MoveConveyorCommand(CONVEYOR_SPEED, conveyor));

    driveController.povUp().onTrue(new MoveIntakeCommand(ArmState.RAISED, intakeArms));
    driveController.povDown().onTrue(new MoveIntakeCommand(ArmState.LOWERED, intakeArms));

    // Add LED command once we have the lights on the robot
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
