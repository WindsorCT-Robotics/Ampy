package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.autonomous.AutoDriveCommand;
import frc.robot.commands.autonomous.AutoScoreCommand;
import frc.robot.commands.drive.DriveCommand;
import frc.robot.subsystems.*;
import frc.robot.subsystems.IntakeArmsSubsystem.ArmState;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
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
  private final CommandXboxController driverController;
  private final CommandXboxController operatorController;

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
    driverController = new CommandXboxController(0);
    operatorController = new CommandXboxController(1);

    // Configure default commands
    drive.setDefaultCommand(
        new DriveCommand(() -> driverController.getLeftY(), () -> getTriggerScale(driverController.getLeftTriggerAxis()),
            () -> driverController.getRightX(), () -> getTriggerScale(driverController.getRightTriggerAxis()), drive));
    conveyor.setDefaultCommand(new MoveConveyorCommand(-0.1, conveyor));
    intakeRollers.setDefaultCommand(
        new MoveIntakeRollersCommand(() -> operatorController.getLeftY() * INTAKE_ROLLER_SPEED, intakeRollers));

    // Configure button bindings
    configureOperatorBindings();
    configureDriverBindings();

    // Initialize autonomous chooser
    chooser = new SendableChooser<>();
    chooser.setDefaultOption("Score short", new AutoScoreCommand(conveyor, drive).withTimeout(6));
    chooser.addOption("Score far", new AutoScoreCommand(conveyor, drive).withTimeout(8));
    chooser.addOption("Drive forward", new AutoDriveCommand(-0.25, 0, drive).withTimeout(2));
    chooser.addOption("Score piece", new MoveConveyorCommand(0.8, conveyor).withTimeout(1));
    chooser.addOption("Do nothing", new PrintCommand("Doing nothing!"));
    chooser.addOption("Drive forward", new AutoDriveCommand(-0.25, 0, drive).withTimeout(2));
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
   * Configure operator controller bindings
   */
  private void configureOperatorBindings() {
    // Rumble when a piece is grabbed
    Trigger getPieceTrigger = new Trigger(() -> conveyor.isEmpty() == true);
    getPieceTrigger
        .onTrue(
            new RunCommand(() -> operatorController.getHID().setRumble(RumbleType.kBothRumble, 0.5)).withTimeout(0.5));

    // set LED color
    operatorController.x().onTrue(new SetLedColorCommand(ledSubsystem, 0, 0, 255));
    operatorController.y().onTrue(new SetLedColorCommand(ledSubsystem, 255, 102, 0));

    operatorController.a().whileTrue(new EjectCommand(intakeArms, conveyor, intakeRollers));

    // Automatic intake
    Trigger leftTrigger = new Trigger(() -> operatorController.getLeftTriggerAxis() > 0.5);
    leftTrigger.onTrue(new IntakeFromFloorCommand(intakeArms, conveyor, intakeRollers));
    Trigger rightTrigger = new Trigger(() -> operatorController.getRightTriggerAxis() > 0.5);
    rightTrigger.onTrue(new IntakeFromSubstationCommand(intakeArms, conveyor, intakeRollers));

    Trigger conveyorTrigger = new Trigger(() -> Math.abs(operatorController.getRightY()) > 0.1);
    conveyorTrigger.whileTrue(
        new MoveConveyorCommand(() -> driverController.getLeftTriggerAxis() * CONVEYOR_SPEED, conveyor));

    operatorController.povUp().onTrue(new MoveIntakeCommand(ArmState.RAISED, intakeArms));
    operatorController.povDown().onTrue(new MoveIntakeCommand(ArmState.LOWERED, intakeArms));
  }

  /**
   * Configure driver controller bindings
   */
  private void configureDriverBindings() {
    // Toggle current limiting and rumble
    driverController.y()
        .onTrue(new InstantCommand(() -> drive.setCurrentLimitingEnabled(!drive.isCurrentLimitingEnabled()))
            .andThen(new RunCommand(() -> driverController.getHID().setRumble(RumbleType.kBothRumble, 0.5))));

    // Emergency intake controls
    driverController.povUp().onTrue(new MoveIntakeCommand(ArmState.RAISED, intakeArms));
    driverController.povDown().onTrue(new MoveIntakeCommand(ArmState.LOWERED, intakeArms));
    driverController.povLeft().whileTrue(new MoveIntakeRollersCommand(INTAKE_ROLLER_SPEED, intakeRollers));
    driverController.povRight().whileTrue(new MoveConveyorCommand(CONVEYOR_SPEED, conveyor));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return chooser.getSelected();
  }

  /**
   * Use one joystick trigger to scale another
   * 
   * @param raw the raw axis value
   * @return number to scale by
   */
  private static double getTriggerScale(double raw) {
    // Don't freeze up the turning
    if (raw < 0.1) {
      return 0.1;
    }
    return 1 - raw;
  }

}
