package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.IntakeArmsSubsystem;

/**
 * Lower Ampy's intake arms
 */
public class LowerIntakeCommand extends CommandBase {

    private final IntakeArmsSubsystem intakeArms;

    public LowerIntakeCommand(IntakeArmsSubsystem intakeArms) {
        this.intakeArms = intakeArms;
        addRequirements(intakeArms);
    }

    @Override
    public void execute() {
        intakeArms.lowerArms();
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
