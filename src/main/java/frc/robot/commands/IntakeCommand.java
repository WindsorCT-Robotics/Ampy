package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IntakeArms;

public class IntakeCommand extends SequentialCommandGroup {

    public IntakeCommand(IntakeArms intakeArms) {
        addCommands(
                new LowerIntakeCommand(intakeArms),

                new ParallelDeadlineGroup(
                        new ForwardConveyorCommand(),
                        new ForwardIntakeRollersCommand()),
                        
                new RaiseIntakeCommand(intakeArms));
    }

}