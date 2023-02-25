package frc.robot.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.IntakeRollersMotorConstants;
import frc.robot.subsystems.IntakeRollers;

public class ForwardIntakeRollersCommand extends CommandBase {
    
    IntakeRollers intakeRollers;
    double velocity;
    double defaultVelocity = IntakeRollersMotorConstants.TARGET_FORWARD_RPM * IntakeRollersMotorConstants.GEAR_RATIO;

    public ForwardIntakeRollersCommand() {
        intakeRollers = IntakeRollers.getInstance();
        addRequirements(intakeRollers);
    }


    @Override
    public void initialize() {
        if (!SmartDashboard.containsKey("ForwardIntakeRollers/VelocitySetPoint")) {
            SmartDashboard.putNumber("ForwardIntakeRollers/VelocitySetPoint", defaultVelocity);
        }
        velocity = SmartDashboard.getNumber("ForwardIntakeRollers/VelocitySetPoint", defaultVelocity);
    }
    
    @Override
    public void execute() {
        intakeRollers.setVelocity(velocity);
    }

    @Override
    public void end(boolean interrupted) {
        intakeRollers.stop();
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

