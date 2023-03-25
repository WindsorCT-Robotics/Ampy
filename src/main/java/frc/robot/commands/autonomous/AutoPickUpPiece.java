package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.IntakeFromFloorCommand;
import frc.robot.commands.MoveConveyorCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeArmsSubsystem;
import frc.robot.subsystems.IntakeRollersSubsystem;

public class AutoPickUpPiece extends SequentialCommandGroup {
    public AutoPickUpPiece(ConveyorSubsystem conveyor, DriveSubsystem drive, IntakeArmsSubsystem intakeArms, IntakeRollersSubsystem intakeRollers) {
        addCommands(
            new MoveConveyorCommand(0.8, conveyor).withTimeout(1), // spit out piece
            new WaitCommand(1), // Wait for cube to settle
            new ParallelDeadlineGroup(
                new AutoDriveCommand(-0.25, 0, drive).withTimeout(4), // go to a piece
                new IntakeFromFloorCommand(intakeArms, conveyor, intakeRollers)), // pick up the piece
            new AutoDriveCommand(-0.25, 0, drive).withTimeout(4) // return to the community

        );
    }
    
}
