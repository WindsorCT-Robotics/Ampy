package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.IntakeArmsSubsystem;
import frc.robot.subsystems.IntakeRollersSubsystem;
import frc.robot.subsystems.IntakeArmsSubsystem.ArmState;

/**
 * This command executes multiple commands in sequence in order to intake an
 * object. It first lowers the intake, then activates the conveyor
 * and intake rollers at the same time with both of them rotating forwards in
 * order to intake an object. Afterwards, it raises the intake.
 */
public class IntakeCommand extends SequentialCommandGroup {

        public IntakeCommand(IntakeArmsSubsystem intakeArms, ConveyorSubsystem conveyor,
                        IntakeRollersSubsystem intakeRollers) {
                addCommands(
                                new MoveIntakeCommand(ArmState.LOWERED, intakeArms),
                                new ParallelCommandGroup(
                                                new MoveConveyorCommand(0.5, conveyor),
                                                new MoveIntakeRollersCommand(0.5, intakeRollers))
                                                .until(() -> !conveyor.isEmpty()),
                                new MoveIntakeCommand(ArmState.RAISED, intakeArms));
        }

}