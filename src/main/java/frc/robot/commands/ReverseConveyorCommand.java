package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;
import frc.robot.Constants.ConveyorMotorConstants;

public class ReverseConveyorCommand extends CommandBase {

    Conveyor conveyor;
    double velocity;
    double defaultVelocity = ConveyorMotorConstants.TARGET_REVERSE_RPM * ConveyorMotorConstants.GEAR_RATIO;

    public ReverseConveyorCommand() {
        conveyor = Conveyor.getInstance();
        addRequirements(conveyor);
    }

    @Override
    public void initialize() {
        if (!SmartDashboard.containsKey("VelocitySetPoint")) {
            SmartDashboard.putNumber("VelocitySetPoint", defaultVelocity);
        }
        velocity = SmartDashboard.getNumber("VelocitySetPoint", defaultVelocity);
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
