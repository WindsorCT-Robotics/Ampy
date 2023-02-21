
package frc.robot.commands;

import frc.robot.subsystems.Conveyor;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurnConveyorCommand extends CommandBase {
    private final Conveyor m_conveyer;
    private double m_speed;

    public TurnConveyorCommand(double speed, Conveyor subsystem) {
        m_speed = speed;
        m_conveyer = subsystem;
        addRequirements(m_conveyer);

    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_conveyer.turn();
    }

    @Override
    public void end(boolean interrupted) {
        m_conveyer.stop();
    }

    @Override
    public boolean isFinished() {
        return (m_conveyer.getFrontInput() && m_conveyer.getBackInput());
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }

}