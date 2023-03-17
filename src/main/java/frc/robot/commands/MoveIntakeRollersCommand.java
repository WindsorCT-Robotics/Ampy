package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeRollersSubsystem;

/**
 * Enable the rollers of Ampy's intake arms to spin forwards (in the direction
 * to intake objects)
 */
public class MoveIntakeRollersCommand extends CommandBase {
    private IntakeRollersSubsystem intakeRollers;
    private DoubleSupplier speed;

    public MoveIntakeRollersCommand(DoubleSupplier speed, IntakeRollersSubsystem intakeRollers) {
        this.speed = speed;
        this.intakeRollers = intakeRollers;
        addRequirements(intakeRollers);
    }

    public MoveIntakeRollersCommand(double speed, IntakeRollersSubsystem intakeRollers) {
        this(() -> speed, intakeRollers);
    }

    @Override
    public void execute() {
        intakeRollers.setSpeed(speed.getAsDouble());
    }

    @Override
    public void end(boolean interrupted) {
        intakeRollers.stop();
    }
}
