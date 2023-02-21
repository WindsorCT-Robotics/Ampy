package frc.robot.commands;

import frc.robot.subsystems.Drive;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class PrecisionToggle extends InstantCommand {
    public static Drive precisionDrive;
    public static boolean precisionToggle;

    public PrecisionToggle(Drive m_drive, boolean precision) {
        precisionDrive = m_drive;
        precisionToggle = precision;
        execute();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        precisionToggle = !precisionToggle;
        precisionDrive.togglePrecisionTurnMode(precisionToggle);
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
