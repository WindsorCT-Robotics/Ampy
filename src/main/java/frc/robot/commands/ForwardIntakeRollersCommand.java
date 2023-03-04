package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeRollersSubsystem;

/**
 * Enable the rollers of Ampy's intake arms to spin forwards (in the direction
 * to intake objects)
 */
public class ForwardIntakeRollersCommand extends CommandBase {
    IntakeRollersSubsystem intakeRollers;
    public static final double TARGET_FORWARD_SPEED = 0.3;

    public ForwardIntakeRollersCommand(IntakeRollersSubsystem intakeRollers) {
        this.intakeRollers = intakeRollers;
        addRequirements(intakeRollers);
    }

    @Override
    public void execute() {
        intakeRollers.setSpeed(TARGET_FORWARD_SPEED);
    }

    @Override
    public void end(boolean interrupted) {
        intakeRollers.stop();
    }
}
