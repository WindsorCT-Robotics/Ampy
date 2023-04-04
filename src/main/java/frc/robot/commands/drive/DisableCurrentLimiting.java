package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DisableCurrentLimiting extends CommandBase {
    private DriveSubsystem drive;

    public DisableCurrentLimiting(DriveSubsystem drive) {
        this.drive = drive;
    }

    @Override
    public void initialize() {
        drive.setCurrentLimitEnabled(false);
    }

    @Override
    public void end(boolean interrupted) {
        drive.setCurrentLimitEnabled(true);
    }

}
