package frc.robot.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeRollersSubsystem;

// This command enables the rollers of Ampy's intake arms to spin forwards (in the direction to intake objects)

public class ForwardIntakeRollersCommand extends CommandBase {
    
    IntakeRollersSubsystem intakeRollers;
    public static final double GEAR_RATIO = 20;
    double velocity;
    double defaultVelocity = ForwardIntakeRollersCommand.TARGET_FORWARD_RPM * GEAR_RATIO;
    public static final double TARGET_FORWARD_RPM = 250; //250 is not an arbitrary number

    public ForwardIntakeRollersCommand() {
        intakeRollers = IntakeRollersSubsystem.getInstance();
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

