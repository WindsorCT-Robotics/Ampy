
package frc.robot.subsystems;

import frc.robot.commands.PrecisionToggle;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Drive;
import frc.robot.commands.DriveCommand;
import frc.robot.Constants.*;
import frc.robot.Constants.TimeConstants;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class Drive extends SubsystemBase {

    private WPI_TalonFX lPrimary;
    private WPI_TalonFX lSecondary;
    private MotorControllerGroup leftDrive;
    private WPI_TalonFX rPrimary;
    private WPI_TalonFX rSecondary;
    private MotorControllerGroup rightDrive;

    public Drive() {

        lPrimary = new WPI_TalonFX(1);
        lSecondary = new WPI_TalonFX(2);

        leftDrive = new MotorControllerGroup(lPrimary, lSecondary);
        addChild("LeftDrive", leftDrive);

        rPrimary = new WPI_TalonFX(3);
        rSecondary = new WPI_TalonFX(4);

        rightDrive = new MotorControllerGroup(rPrimary, rSecondary);
        addChild("RightDrive", rightDrive);

    }

    public synchronized static Drive getInstance() {
        if (PrecisionToggle.precisionDrive == null) {
            PrecisionToggle.precisionDrive = new Drive();
        }
        return PrecisionToggle.precisionDrive;
    }

    @Override
    public void periodic() {

    }

    @Override
    public void simulationPeriodic() {

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void togglePrecisionTurnMode(boolean precisionToggle) {
        if (!precisionToggle) {
            lPrimary.setNeutralMode(NeutralMode.Brake); // Make sure NeutralMode.Brake is for regular mode, not
                                                        // precision
            rPrimary.setNeutralMode(NeutralMode.Brake);
            lSecondary.setNeutralMode(NeutralMode.Brake);
            rSecondary.setNeutralMode(NeutralMode.Brake);
            lPrimary.configOpenloopRamp(0);
            rPrimary.configOpenloopRamp(0);
            lSecondary.configOpenloopRamp(0);
            rSecondary.configOpenloopRamp(0);
        } else {
            lPrimary.setNeutralMode(NeutralMode.Coast); // Make sure NeutralMode.Coast is for precision mode, not
                                                        // regular
            rPrimary.setNeutralMode(NeutralMode.Coast);
            lSecondary.setNeutralMode(NeutralMode.Coast);
            rSecondary.setNeutralMode(NeutralMode.Coast);
            lPrimary.configOpenloopRamp(TimeConstants.RAMP_TIME);
            rPrimary.configOpenloopRamp(TimeConstants.RAMP_TIME);
            lSecondary.configOpenloopRamp(TimeConstants.RAMP_TIME);
            rSecondary.configOpenloopRamp(TimeConstants.RAMP_TIME);
        }
    }

}
