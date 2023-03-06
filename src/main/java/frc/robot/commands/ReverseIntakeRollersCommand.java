package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeRollersSubsystem;

/**
 * This command enables the rollers of Ampy's intake arms to spin in reverse (in
 * the direction to eject objects)
 */
public class ReverseIntakeRollersCommand extends CommandBase {

    IntakeRollersSubsystem intakeRollers;
    public static final double TARGET_REVERSE_SPEED = -0.3;

    public ReverseIntakeRollersCommand(IntakeRollersSubsystem intakeRollers) {
        this.intakeRollers = intakeRollers;
        addRequirements(intakeRollers);
    }

    @Override
    public void execute() {
        intakeRollers.setSpeed(TARGET_REVERSE_SPEED);
    }

    @Override
    public void end(boolean interrupted) {
        intakeRollers.stop();
    }
}
