package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;


/**
 * Enable the conveyor within Ampy to rotate in reverse (in the direction out of the intake)
 */
public class ReverseConveyorCommand extends CommandBase {

    ConveyorSubsystem conveyor;
    public static final double TARGET_REVERSE_SPEED = 0.3;

    public ReverseConveyorCommand(ConveyorSubsystem conveyor) {
        this.conveyor = conveyor;
        addRequirements(conveyor);
    }

    @Override
    public void execute() {
        conveyor.setSpeed(TARGET_REVERSE_SPEED);
    }

    @Override
    public void end(boolean interrupted) {
        conveyor.stop();
    }

}
