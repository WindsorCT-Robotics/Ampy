package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IntakeArms;

public class UntakeCommand extends SequentialCommandGroup {

    public UntakeCommand(IntakeArms intakeArms) {
        addCommands(
                new LowerIntakeCommand(intakeArms),

                new ParallelDeadlineGroup(
                        new ReverseConveyorCommand(),
                        new ReverseIntakeRollersCommand()),

                new RaiseIntakeCommand(intakeArms));
    }

}