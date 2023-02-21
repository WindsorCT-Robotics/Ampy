
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.subsystems.IntakeArms;

public class LowerIntakeCommand extends InstantCommand {

    private final IntakeArms m_intakeArms;

    public LowerIntakeCommand(IntakeArms subsystem) {

        m_intakeArms = subsystem;
        addRequirements(m_intakeArms);

    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_intakeArms.retract();
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
