package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.hardware.WPI_CANSparkMax;

public class ConveyorSubsystem extends SubsystemBase {

    private WPI_CANSparkMax conveyorMotor;
    private static final int CONVEYOR_MOTOR_CAN_ID = 5; // CAN ID should be 5, it may be different as a result of testing
    private static final int INTAKE_SENSOR_CHANNEL = 0;
    private final DigitalInput intakeSensor;
    private static final int CONVEYOR_SENSOR_CHANNEL = 2;
    private final DigitalInput conveyorSensor;
    private final SlewRateLimiter limiter = new SlewRateLimiter(0.5);

    public ConveyorSubsystem() {
        intakeSensor = new DigitalInput(INTAKE_SENSOR_CHANNEL);
        conveyorSensor = new DigitalInput(CONVEYOR_SENSOR_CHANNEL);
        conveyorMotor = new WPI_CANSparkMax(CONVEYOR_MOTOR_CAN_ID, MotorType.kBrushless);
        conveyorMotor.restoreFactoryDefaults();
        addChild("Conveyor motor", conveyorMotor);
        initializeSmartDashboard();
    }

    private void initializeSmartDashboard() {
        SmartDashboard.putBoolean("isConveyorEmpty", isIntakeSensor());
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("isConveyorEmpty", isIntakeSensor());
    }

    /**
     * Set the conveyor's speed
     * @param speed the speed in [-1.0, 1.0]
     */
    public void setSpeed(double speed) {
        conveyorMotor.set(limiter.calculate(speed));
    }

    public void stop() {
        conveyorMotor.stopMotor();
    }

    /**
     * Get the intake sensor status
     * @return whether something is in the intake
     */
    public boolean isIntakeSensor() {
        return intakeSensor.get();
    }

    /**
     * Get the conveyor sensor status
     * @return whether something is on the conveyor
     */
    public boolean isConveyorSensor() {
        return conveyorSensor.get();
    }
}
