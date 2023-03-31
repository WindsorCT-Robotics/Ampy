package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.IntakeArmsSubsystem;
import frc.robot.subsystems.IntakeArmsSubsystem.ArmState;

public class IntakeFromSubstationCommand extends CommandBase {
        IntakeArmsSubsystem intakeArms;
        ConveyorSubsystem conveyor;

        public IntakeFromSubstationCommand(IntakeArmsSubsystem intakeArms, ConveyorSubsystem conveyor) {
                this.intakeArms = intakeArms;
                this.conveyor = conveyor;
                addRequirements(intakeArms);
        }

        @Override
        public void initialize() {
                intakeArms.setArmState(ArmState.LOWERED);
        }

        @Override
        public boolean isFinished() {
                return !(conveyor.isConveyorSensor() || conveyor.isIntakeSensor());
        }

        @Override
        public void end(boolean interrupted) {
                intakeArms.setArmState(ArmState.RAISED);
        }

}