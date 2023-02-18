package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class UntakeCommand extends SequentialCommandGroup {

    public UntakeCommand() {
        addCommands(
                new LowerIntakeCommand(),

                new ParallelDeadlineGroup(
                        new ReverseConveyorCommand(),
                        new ReverseIntakeRollersCommand()),
                        
                new RaiseIntakeCommand());
    }

}