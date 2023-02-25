package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

// This command enables the conveyor within Ampy to rotate forward (in the direction into the intake)

public class ForwardConveyorCommand extends CommandBase {

    ConveyorSubsystem conveyor;
    double velocity;
    public static final int GEAR_RATIO = 20;
    public static final int TARGET_FORWARD_RPM = 250; // 250 is not an arbitrary number
    double defaultVelocity = TARGET_FORWARD_RPM * GEAR_RATIO;

    public ForwardConveyorCommand() {
        conveyor = ConveyorSubsystem.getInstance();
        addRequirements(conveyor);
    }

    @Override
    public void initialize() {
        if (!SmartDashboard.containsKey("ForwardConveyor/VelocitySetPoint")) {
            SmartDashboard.putNumber("ForwardConveyor/VelocitySetPoint", defaultVelocity);
        }
        velocity = SmartDashboard.getNumber("ForwardConveyor/VelocitySetPoint", defaultVelocity);
    }

    @Override
    public void execute() {
        conveyor.setVelocity(velocity);
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
