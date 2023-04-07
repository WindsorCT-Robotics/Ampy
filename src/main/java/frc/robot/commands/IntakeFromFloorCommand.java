package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
public class IntakeFromFloorCommand extends SequentialCommandGroup {
        private final double CONVEYOR_SPEED = 0.3;
        private final double INTAKE_ROLLER_SPEED = -0.5;
        public IntakeFromFloorCommand(IntakeArmsSubsystem intakeArms, ConveyorSubsystem conveyor,
                        IntakeRollersSubsystem intakeRollers) {
                addCommands(
                                new MoveIntakeCommand(ArmState.LOWERED, intakeArms),
                                new ParallelCommandGroup(
                                                new MoveConveyorCommand(CONVEYOR_SPEED, conveyor),
                                                new MoveIntakeRollersCommand(INTAKE_ROLLER_SPEED, intakeRollers))
                                                .until(() -> !conveyor.isIntakeSensor() || !conveyor.isConveyorSensor()),
                                new MoveIntakeCommand(ArmState.RAISED, intakeArms),
                                new WaitCommand(1),
                                new MoveConveyorCommand(-CONVEYOR_SPEED, conveyor).withTimeout(0.5));
        }

}