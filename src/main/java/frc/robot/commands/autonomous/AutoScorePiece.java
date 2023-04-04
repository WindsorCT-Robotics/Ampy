package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeArmsSubsystem;
import frc.robot.subsystems.IntakeRollersSubsystem;

public class AutoScorePiece extends SequentialCommandGroup {

    public AutoScorePiece(Alliance alliance, ConveyorSubsystem conveyor, DriveSubsystem drive,
            IntakeArmsSubsystem intakeArms, IntakeRollersSubsystem intakeRollers) {
        double turnDirection = (alliance == Alliance.Red) ? -1 : 1;
        addCommands(new AutoPickUpPiece(conveyor, drive, intakeArms, intakeRollers),
                new AutoDriveCommand(0, 0.25 * turnDirection, drive).withTimeout(0.5),
                new AutoDriveCommand(0.25, 0, drive).withTimeout(0.5),
                new AutoScoreCommand(conveyor));
    }

}
