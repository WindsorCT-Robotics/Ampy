package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
// import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.subsystems.IntakeRollers;

public class IntakeCommand extends SequentialCommandGroup {

    public IntakeCommand(IntakeRollers rollers) {

//         addCommands(
//             new LowerIntakeCommand(),

//             new ParallelDeadlineGroup(
//                 new TurnConveyorCommand(),
//                 new RunIntakeCommand(),
//             )
//         );
    }
}