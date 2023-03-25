package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

/**
 * Enable the conveyor within Ampy to rotate forward (in the direction into the intake)
 */
public class MoveConveyorCommand extends CommandBase {
    ConveyorSubsystem conveyor;
    private final DoubleSupplier targetSpeed;

    public MoveConveyorCommand(DoubleSupplier targetSpeed, ConveyorSubsystem conveyor) {
        this.targetSpeed = targetSpeed;
        this.conveyor = conveyor;
        addRequirements(conveyor);
    }

    public MoveConveyorCommand(double targetSpeed, ConveyorSubsystem conveyor) {
        this(() -> targetSpeed, conveyor);
    }

    @Override
    public void execute() {
        conveyor.setSpeed(targetSpeed.getAsDouble());
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
