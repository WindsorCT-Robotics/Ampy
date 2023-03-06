package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeArmsSubsystem;
import frc.robot.subsystems.IntakeArmsSubsystem.ArmState;

/**
 * Raise Ampy's intake arms
 */
public class MoveIntakeCommand extends InstantCommand {

    private final IntakeArmsSubsystem intakeArms;
    private final ArmState state;

    public MoveIntakeCommand(ArmState state, IntakeArmsSubsystem intakeArms) {
        this.intakeArms = intakeArms;
        this.state = state;
        addRequirements(intakeArms);
    }

    @Override
    public void execute() {
        intakeArms.setArmState(state);
    }
}
