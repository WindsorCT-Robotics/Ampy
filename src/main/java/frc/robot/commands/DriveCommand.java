
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.DoubleSupplier;
import frc.robot.subsystems.Drive;

public class DriveCommand extends CommandBase {

    private final Drive m_drive;
    private final DoubleSupplier m_forward;
    private final DoubleSupplier m_turn;

    public DriveCommand(DoubleSupplier forward, DoubleSupplier turn) {

        m_forward = forward;
        m_turn = turn;

        m_drive = Drive.getInstance();
        addRequirements(m_drive);

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
