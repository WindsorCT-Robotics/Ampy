package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.Constants.ConveyorMotorConstants;

public class ReverseConveyorCommand extends CommandBase {

    ConveyorSubsystem conveyor;
    double velocity;
    double defaultVelocity = ConveyorMotorConstants.TARGET_REVERSE_RPM * ConveyorMotorConstants.GEAR_RATIO;

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
