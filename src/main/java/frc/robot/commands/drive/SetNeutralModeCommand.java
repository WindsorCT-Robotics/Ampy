package frc.robot.commands.drive;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.DriveSubsystem;

/**
 * Command to set the drivetrain's neutral mode
 * @see frc.robot.Subsystems.DrivetrainSubsystem#setNeutralMode
 */
public class SetNeutralModeCommand extends InstantCommand {
    private final NeutralMode brakeMode;
    private DriveSubsystem drivetrainSubsystem;

    public SetNeutralModeCommand(NeutralMode brakeMode, DriveSubsystem drivetrainSubsystem) {
        this.brakeMode = brakeMode;
        this.drivetrainSubsystem = drivetrainSubsystem;
    }

    @Override
    public void execute() {
        drivetrainSubsystem.setNeutralMode(brakeMode);
    }
    
}
