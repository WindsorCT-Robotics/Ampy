package frc.robot.commands.autonomous;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoDriveCommand extends CommandBase{
    DriveSubsystem drive;
    Timer elapsedTime;
    double seconds;
    double speed;
    double curvature;
    public AutoDriveCommand(double speed, double curvature, DriveSubsystem drive) {
        addRequirements(drive);
        this.drive = drive;
        this.speed = speed;
        this.curvature = curvature;
    }

    @Override
    public void initialize() {
        drive.setNeutralMode(NeutralMode.Brake);
    }
    
    @Override
    public void execute() {
        drive.drive(speed, curvature);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

}
