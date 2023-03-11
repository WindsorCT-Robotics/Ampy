package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeRollersSubsystem;

/**
 * Enable the rollers of Ampy's intake arms to spin forwards (in the direction
 * to intake objects)
 */
public class MoveIntakeRollersCommand extends CommandBase {
    IntakeRollersSubsystem intakeRollers;
    private final double speed;

    public MoveIntakeRollersCommand(double speed, IntakeRollersSubsystem intakeRollers) {
        this.speed = speed;
        this.intakeRollers = intakeRollers;
        addRequirements(intakeRollers);
    }

    @Override
    public void execute() {
        intakeRollers.setSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
        intakeRollers.stop();
    }
}
