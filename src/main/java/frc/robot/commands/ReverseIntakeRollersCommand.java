package frc.robot.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.IntakeRollersMotorConstants;
import frc.robot.subsystems.IntakeRollersSubsystem;

public class ReverseIntakeRollersCommand extends CommandBase {
    
    IntakeRollersSubsystem intakeRollers;
    double velocity;
    double defaultVelocity = IntakeRollersMotorConstants.TARGET_REVERSE_RPM * IntakeRollersMotorConstants.GEAR_RATIO;

    public ReverseIntakeRollersCommand() {
        intakeRollers = IntakeRollersSubsystem.getInstance();
        addRequirements(intakeRollers);
    }


    @Override
    public void initialize() {
        if (!SmartDashboard.containsKey("ReverseIntakeRollers/VelocitySetPoint")) {
            SmartDashboard.putNumber("ReverseIntakeRollers/VelocitySetPoint", defaultVelocity);
        }
        velocity = SmartDashboard.getNumber("ReverseIntakeRollers/VelocitySetPoint", defaultVelocity);
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

