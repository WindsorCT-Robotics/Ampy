package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;
import frc.robot.Constants.ConveyorMotorConstants;

public class ForwardConveyorCommand extends CommandBase {

    Conveyor conveyor;
    double velocity;
    double defaultVelocity = ConveyorMotorConstants.TARGET_FORWARD_RPM * ConveyorMotorConstants.GEAR_RATIO;

    public ForwardConveyorCommand() {
        conveyor = Conveyor.getInstance();
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
