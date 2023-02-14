package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Conveyor extends SubsystemBase {

    private static Conveyor conveyor;

    private CANSparkMax conveyorMotor;
    private final int deviceId = 5;
    private final double maxRPM = 2000;
    private SparkMaxPIDController pidController;
    private RelativeEncoder encoder;
    private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    private Conveyor() {
        conveyorMotor = new CANSparkMax(deviceId, MotorType.kBrushless);
        conveyorMotor.restoreFactoryDefaults();
        pidController = conveyorMotor.getPIDController();
        encoder = conveyorMotor.getEncoder();
        initializePidController();
        initializeSmartDashboard();
    }

    public static synchronized Conveyor getInstance() {
        if (null == conveyor) {
            conveyor = new Conveyor();
        }
        return conveyor;
    }

    private void initializePidController() {
        // PID coefficients
        kP = 0.0001;
        kI = 0.000001;
        kD = 0.00002;
        kIz = 0;
        kFF = 0.000015;
        kMaxOutput = 1;
        kMinOutput = -1;

        // set PID coefficients
        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setIZone(kIz);
        pidController.setFF(kFF);
        pidController.setOutputRange(kMinOutput, kMaxOutput);
    }

    private void initializeSmartDashboard() {
        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);
    }

    @Override
    public void periodic() {
        // read PID coefficients from SmartDashboard
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);

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
        if ((max != kMaxOutput) || (min != kMinOutput)) {
            pidController.setOutputRange(min, max);
            kMinOutput = min;
            kMaxOutput = max;
        }
    }

    /**
     * PIDController objects are commanded to a set point using the
     * SetReference() method.
     * 
     * The first parameter is the value of the set point, whose units vary
     * depending on the control type set in the second parameter.
     * 
     * The second parameter is the control type can be set to one of four
     * parameters:
     * com.revrobotics.CANSparkMax.ControlType.kDutyCycle
     * com.revrobotics.CANSparkMax.ControlType.kPosition
     * com.revrobotics.CANSparkMax.ControlType.kVelocity
     * com.revrobotics.CANSparkMax.ControlType.kVoltage
     */
    public void setVelocity(double velocity) {
        if (velocity > maxRPM) {
            velocity = maxRPM;
        } else if (velocity < -maxRPM) {
            velocity = -maxRPM;
        }
        pidController.setReference(velocity, CANSparkMax.ControlType.kVelocity);
        SmartDashboard.putNumber("Current Velocity", encoder.getVelocity());
    }

    public void stop() {
        conveyorMotor.stopMotor();
        SmartDashboard.putNumber("Current Velocity", 0.0);
    }
}
