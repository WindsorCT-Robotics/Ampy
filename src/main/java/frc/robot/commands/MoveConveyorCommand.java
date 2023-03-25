package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

/**
 * Enable the conveyor within Ampy to rotate forward (in the direction into the intake)
 */
public class MoveConveyorCommand extends CommandBase {
    ConveyorSubsystem conveyor;
    private final double speed;
    private final SlewRateLimiter smoothing = new SlewRateLimiter(1);

    public MoveConveyorCommand(double targetSpeed, ConveyorSubsystem conveyor) {
        // Positive values move pieces towards the back
        this.speed = -targetSpeed;
        this.conveyor = conveyor;
        addRequirements(conveyor);
    }

    @Override
    public void execute() {
        conveyor.setSpeed(smoothing.calculate(speed));
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        conveyor.stop();
    }

}
