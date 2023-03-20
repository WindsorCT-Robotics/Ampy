package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.MoveConveyorCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class AutoScoreCommand extends SequentialCommandGroup {
    public AutoScoreCommand(ConveyorSubsystem conveyor, DriveSubsystem drive) {
        addCommands(
            new MoveConveyorCommand(0.8, conveyor).withTimeout(1), // spit out piece
            new WaitCommand(1),
            new AutoDriveCommand(-0.25, 0, drive) // escape the community
        );
    }
    
}
