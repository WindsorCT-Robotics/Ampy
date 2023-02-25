package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ConveyorMotorConstants;
import frc.robot.Constants.DigitalConstants;

public class Conveyor extends SubsystemBase {

    private static Conveyor conveyor;
    private DigitalInput conveyorFullSensor;
    private CANSparkMax conveyorMotor;
    private SparkMaxPIDController pidController;
    private RelativeEncoder encoder;
    private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    private Conveyor() {
        conveyorFullSensor = new DigitalInput(DigitalConstants.conveyorFullSensor);
        conveyorMotor = new CANSparkMax(ConveyorMotorConstants.CAN_ID, MotorType.kBrushless);
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
        kP = ConveyorMotorConstants.kP;
        kI = ConveyorMotorConstants.kI;
        kD = ConveyorMotorConstants.kD;
        kIz = ConveyorMotorConstants.kIz;
        kFF = ConveyorMotorConstants.kFF;
        kMaxOutput = ConveyorMotorConstants.MAX_POWER_OUTPUT;
        kMinOutput = ConveyorMotorConstants.MIN_POWER_OUTPUT;

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
        SmartDashboard.putNumber("Conveyor/P Gain", kP);
        SmartDashboard.putNumber("Conveyor/I Gain", kI);
        SmartDashboard.putNumber("Conveyor/D Gain", kD);
        SmartDashboard.putNumber("Conveyor/I Zone", kIz);
        SmartDashboard.putNumber("Conveyor/Feed Forward", kFF);
        SmartDashboard.putNumber("Conveyor/Max Output", kMaxOutput);
        SmartDashboard.putNumber("Conveyor/Min Output", kMinOutput);
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
        double max = SmartDashboard.getNumber("Conveyor/Max Output", 0);
        double min = SmartDashboard.getNumber("Conveyor/Min Output", 0);
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
        if (velocity > ConveyorMotorConstants.MAX_RPM) {
            velocity = ConveyorMotorConstants.MAX_RPM;
        } else if (velocity < -ConveyorMotorConstants.MAX_RPM) {
            velocity = -ConveyorMotorConstants.MAX_RPM;
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
