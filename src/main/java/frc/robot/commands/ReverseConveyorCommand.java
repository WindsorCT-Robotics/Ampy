package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

// This command enables the conveyor within Ampy to rotate in reverse (in the direction out of the intake)

public class ReverseConveyorCommand extends CommandBase {

    ConveyorSubsystem conveyor;
    double velocity;
    double defaultVelocity = ReverseConveyorCommand.TARGET_REVERSE_RPM * ReverseConveyorCommand.GEAR_RATIO;
    public static final double GEAR_RATIO = 20;
    public static final double TARGET_REVERSE_RPM = -200; //-200 is an arbitrary number chosen by Evan

    public ReverseConveyorCommand() {
        conveyor = ConveyorSubsystem.getInstance();
        addRequirements(conveyor);
    }

    @Override
    public void initialize() {
        if (!SmartDashboard.containsKey("ReverseConveyor/VelocitySetPoint")) {
            SmartDashboard.putNumber("ReverseConveyor/VelocitySetPoint", defaultVelocity);
        }
        velocity = SmartDashboard.getNumber("ReverseConveyor/VelocitySetPoint", defaultVelocity);
    }

    @Override
    public void execute() {
        conveyor.setVelocity(velocity);
    }

    @Override
    public void end(boolean interrupted) {
        conveyor.stop();
    }
    
}
