package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.IntakeArmsSubsystem;
import frc.robot.subsystems.IntakeRollersSubsystem;

/*
This command executes multiple commands in sequence in order to intake an object. It first lowers the intake, then activates the conveyor
and intake rollers at the same time with both of them rotating forwards in order to intake an object. Afterwards, it raises the intake.
*/
public class IntakeCommand extends SequentialCommandGroup {

    public IntakeCommand(IntakeArmsSubsystem intakeArms, ConveyorSubsystem conveyor,
            IntakeRollersSubsystem intakeRollers) {
        addCommands(
                new LowerIntakeCommand(intakeArms),
                new ParallelDeadlineGroup(
                        new ForwardConveyorCommand(conveyor),
                        new ForwardIntakeRollersCommand(intakeRollers)),
                new RaiseIntakeCommand(intakeArms));
    }

}