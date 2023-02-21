package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.DoubleSupplier;
import frc.robot.subsystems.DriveSubsystem;


/**
 *
 */
public class DriveCommand extends CommandBase {
    
    private final DriveSubsystem drive;
    private final DoubleSupplier speed;
    private final DoubleSupplier rotation;

    public DriveCommand(DoubleSupplier speed, DoubleSupplier rotation, DriveSubsystem drive) {

        this.speed = speed;
        this.rotation = rotation;

        this.drive = drive;
        addRequirements(drive);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        drive.drive(speed.getAsDouble(), rotation.getAsDouble());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;

    }
}
