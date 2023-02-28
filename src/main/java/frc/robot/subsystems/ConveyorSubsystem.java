package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.hardware.WPI_CANSparkMax;

public class ConveyorSubsystem extends SubsystemBase {

    private static ConveyorSubsystem conveyor;
    private WPI_CANSparkMax conveyorMotor;
    private SparkMaxPIDController pidController;
    private RelativeEncoder encoder;
    public static DigitalInput conveyorFullSensor;
    private double kFF = 0.000015;
    private double kIz = 0;
    private double kD = 0.00002;
    private double kI = 0.000001;
    private double kP = 0.0001;
    private final double MIN_POWER_OUTPUT = -1; // Min power output out of 100%
    private final double MAX_POWER_OUTPUT = 1; // Max power output out of 100%
    private final double MAX_RPM = 2000;
    private static final int CONVEYOR_MOTOR_CAN_ID = 5; // CAN ID should be 5, it may be different as a result of testing
    private static final int CONVEYOR_SENSOR_CAN_ID = 0;

    private ConveyorSubsystem() {
        conveyorFullSensor = new DigitalInput(CONVEYOR_SENSOR_CAN_ID);
        conveyorMotor = new WPI_CANSparkMax(CONVEYOR_MOTOR_CAN_ID, MotorType.kBrushless);
        conveyorMotor.restoreFactoryDefaults();
        addChild("Conveyor motor", conveyorMotor);

        pidController = conveyorMotor.getPIDController();
        encoder = conveyorMotor.getEncoder();
        initializePidController();
        initializeSmartDashboard();
    }

    public static synchronized ConveyorSubsystem getInstance() {
        if (null == conveyor) {
            conveyor = new ConveyorSubsystem();
        }
        return conveyor;
    }

    private void initializePidController() {
        // set PID coefficients
        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setIZone(kIz);
        pidController.setFF(kFF);
        pidController.setOutputRange(MIN_POWER_OUTPUT, MAX_POWER_OUTPUT);
    }

    private void initializeSmartDashboard() {
        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("Conveyor/P Gain", kP);
        SmartDashboard.putNumber("Conveyor/I Gain", kI);
        SmartDashboard.putNumber("Conveyor/D Gain", kD);
        SmartDashboard.putNumber("Conveyor/I Zone", kIz);
        SmartDashboard.putNumber("Conveyor/Feed Forward", kFF);
        SmartDashboard.putNumber("Conveyor/Max Output", MAX_POWER_OUTPUT);
        SmartDashboard.putNumber("Conveyor/Min Output", MIN_POWER_OUTPUT);
        SmartDashboard.putBoolean("isConveyorEmpty", isEmpty());
    }

    @Override
    public void periodic() {
        // read PID coefficients from SmartDashboard
        double p = SmartDashboard.getNumber("Conveyor/P Gain", 0);
        double i = SmartDashboard.getNumber("Conveyor/I Gain", 0);
        double d = SmartDashboard.getNumber("Conveyor/D Gain", 0);
        double iz = SmartDashboard.getNumber("Conveyor/I Zone", 0);
        double ff = SmartDashboard.getNumber("Conveyor/Feed Forward", 0);
        SmartDashboard.putBoolean("isConveyorEmpty", isEmpty());

        // if PID coefficients on SmartDashboard have changed, write new values to
        // controller
        if ((p != kP)) {
            pidController.setP(p);
            kP = p;
        }
        if ((i != kI)) {
            pidController.setI(i);
            kI = i;
        }
        if ((d != kD)) {
            pidController.setD(d);
            kD = d;
        }
        if ((iz != kIz)) {
            pidController.setIZone(iz);
            kIz = iz;
        }
        if ((ff != kFF)) {
            pidController.setFF(ff);
            kFF = ff;
        }
       
    }

    public void setVelocity(double velocity) {
        // Ensures the velocity entered on the Smart Dashboard does not exceed the max
        // velocity
        if (velocity > MAX_RPM) {
            velocity = MAX_RPM;
            // Ensures the velocity entered on the Smart Dashboard does not exceed the max
            // velocity, but in the negative direction
        } else if (velocity < -MAX_RPM) {
            velocity = -MAX_RPM;
        }
        pidController.setReference(velocity, CANSparkMax.ControlType.kVelocity);
        SmartDashboard.putNumber("Conveyor/Current Velocity", encoder.getVelocity());
    }

    public void stop() {
        conveyorMotor.stopMotor();
        SmartDashboard.putNumber("Conveyor/Current Velocity", 0.0);
    }

    public boolean isEmpty() {
        return conveyorFullSensor.get();
    }
}
