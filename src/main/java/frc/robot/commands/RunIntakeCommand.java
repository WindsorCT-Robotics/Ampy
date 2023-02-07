package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeRollers;

public class RunIntakeCommand extends CommandBase {
    private final IntakeRollers m_intakeRollers;

    public RunIntakeCommand(IntakeRollers subsystem) {
        m_intakeRollers = subsystem;
        addRequirements(m_intakeRollers);
    }


    @Override
    public void initialize() {
    }
    
    @Override
    public void execute() {
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

