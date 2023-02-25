
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.subsystems.IntakeArmsSubsystem;

// This command lowers Ampy's intake arms.

public class LowerIntakeCommand extends InstantCommand {

    private final IntakeArmsSubsystem intakeArms;

    public LowerIntakeCommand(IntakeArmsSubsystem intakeArms) {

        this.intakeArms = IntakeArmsSubsystem.getInstance();
        addRequirements(intakeArms);

    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        intakeArms.lowerArms();
    }

    @Override
    public void end(boolean interrupted) {
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
