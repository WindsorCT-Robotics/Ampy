package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

/**
 * Enable the conveyor within Ampy to rotate forward (in the direction into the intake)
 */
public class ForwardConveyorCommand extends CommandBase {
    ConveyorSubsystem conveyor;
    public static final double TARGET_FORWARD_RPM = 0.3;

    public ForwardConveyorCommand(ConveyorSubsystem conveyor) {
        this.conveyor = conveyor;
        addRequirements(conveyor);
    }

    @Override
    public void execute() {
        conveyor.setSpeed(TARGET_FORWARD_RPM);
    }

    @Override
    public boolean isFinished() {
        return !conveyor.isEmpty();
    }

    @Override
    public void end(boolean interrupted) {
        conveyor.stop();
    }

}
