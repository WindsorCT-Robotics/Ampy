
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.DoubleSupplier;
import frc.robot.subsystems.Drive;

public class DriveCommand extends CommandBase {
    
    private final Drive drive;
    private final DoubleSupplier speed;
    private final DoubleSupplier rotation;

    public DriveCommand(DoubleSupplier speed, DoubleSupplier rotation, Drive drive) {

        this.speed = speed;
        this.rotation = rotation;

        this.drive = drive;
        addRequirements(drive);

    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        drive.drive(speed.getAsDouble(), rotation.getAsDouble());
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
