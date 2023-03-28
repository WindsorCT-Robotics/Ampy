
package frc.robot.subsystems;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.hardware.WPI_CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class IntakeRollersSubsystem extends SubsystemBase {

    private WPI_CANSparkMax intakeRollerMotor;
    public static final int CAN_ID = 6; // CAN ID should be 6, it may be different as a result of testing

    public IntakeRollersSubsystem() {
        intakeRollerMotor = new WPI_CANSparkMax(IntakeRollersSubsystem.CAN_ID, MotorType.kBrushless);
        intakeRollerMotor.restoreFactoryDefaults();
        addChild("Intake Roller Motor", intakeRollerMotor);

    }

    /**
     * Set the intake roller's speed
     * @param speed The speed in [-1.0, 1.0]
     */
    public void setSpeed(double speed) {
        SlewRateLimiter filter = new SlewRateLimiter(0.5);
        intakeRollerMotor.set(filter.calculate(speed));
    }

    public void stop() {
        intakeRollerMotor.stopMotor();
    }

}
