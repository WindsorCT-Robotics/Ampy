package frc.robot.commands.AutonomousCommands;

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
    public AutoDriveCommand(double speed, double curvature, double seconds, DriveSubsystem drive) {
        addRequirements(drive);
        this.drive = drive;
        this.speed = speed;
        this.curvature = curvature;
        this.seconds = seconds;
        elapsedTime = new Timer();
    }

    @Override
    public void initialize() {
        drive.setNeutralMode(NeutralMode.Brake);
        elapsedTime.start();
    }
    
    @Override
    public void execute() {
        drive.drive(speed, curvature);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    @Override
    public boolean isFinished() {
        return (elapsedTime.get() >= seconds);
    }
}
