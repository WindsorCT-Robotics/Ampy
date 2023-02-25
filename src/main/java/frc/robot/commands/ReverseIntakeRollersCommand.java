package frc.robot.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeRollersSubsystem;

// This command enables the rollers of Ampy's intake arms to spin in reverse (in the direction to eject objects)

public class ReverseIntakeRollersCommand extends CommandBase {
    
    IntakeRollersSubsystem intakeRollers;
    double velocity;
    public static final double GEAR_RATIO = 20;
    public static final double TARGET_REVERSE_RPM = -200; //-200 is an arbitrary number chosen by Evan
    double defaultVelocity = TARGET_REVERSE_RPM * GEAR_RATIO;
    

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

