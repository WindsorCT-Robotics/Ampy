package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.IntakeArmsSubsystem;

/**
 * Raise Ampy's intake arms
 */
public class RaiseIntakeCommand extends CommandBase {

    private final IntakeArmsSubsystem intakeArms;

    public RaiseIntakeCommand(IntakeArmsSubsystem intakeArms) {
        this.intakeArms = intakeArms;
        addRequirements(intakeArms);
    }

    @Override
    public void execute() {
        intakeArms.raiseArms();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {

        return false;

    }
}
