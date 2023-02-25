
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class IntakeRollersSubsystem extends SubsystemBase {

    private CANSparkMax intakeRollerMotor;
    private static IntakeRollersSubsystem intakeRollers;
    private SparkMaxPIDController pidController;
    private RelativeEncoder encoder;
    private double kFF = 0.000015;
    private double kIz = 0;
    private double kD = 0.00002;
    private double kI = 0.000001;
    private double kP = 0.0001;
    private final double MIN_POWER_OUTPUT = -1; // Min power output out of 100%
    private final double MAX_POWER_OUTPUT = 1; // Max power output out of 100%
    private final double MAX_RPM = 2000;
    public static final int CAN_ID = 6; // CAN ID should be 6

    private IntakeRollersSubsystem() {

        intakeRollerMotor = new CANSparkMax(IntakeRollersSubsystem.CAN_ID, MotorType.kBrushless);
        intakeRollerMotor.restoreFactoryDefaults();
        pidController = intakeRollerMotor.getPIDController();
        encoder = intakeRollerMotor.getEncoder();
        initializePidController();
        initializeSmartDashboard();

    }

    public static synchronized IntakeRollersSubsystem getInstance() {
        if (null == intakeRollers) {
            intakeRollers = new IntakeRollersSubsystem();
        }
        return intakeRollers;
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
        SmartDashboard.putNumber("IntakeRollers/P Gain", kP);
        SmartDashboard.putNumber("IntakeRollers/I Gain", kI);
        SmartDashboard.putNumber("IntakeRollers/D Gain", kD);
        SmartDashboard.putNumber("IntakeRollers/I Zone", kIz);
        SmartDashboard.putNumber("IntakeRollers/Feed Forward", kFF);
        SmartDashboard.putNumber("IntakeRollers/Max Output", MAX_POWER_OUTPUT);
        SmartDashboard.putNumber("IntakeRollers/Min Output", MIN_POWER_OUTPUT);
    }

    @Override
    public void periodic() {
        // read PID coefficients from SmartDashboard
        double p = SmartDashboard.getNumber("IntakeRollers/P Gain", 0);
        double i = SmartDashboard.getNumber("IntakeRollers/I Gain", 0);
        double d = SmartDashboard.getNumber("IntakeRollers/D Gain", 0);
        double iz = SmartDashboard.getNumber("IntakeRollers/I Zone", 0);
        double ff = SmartDashboard.getNumber("IntakeRollers/Feed Forward", 0);
        double max = SmartDashboard.getNumber("IntakeRollers/Max Output", 0);
        double min = SmartDashboard.getNumber("IntakeRollers/Min Output", 0);

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
        SmartDashboard.putNumber("IntakeRollers/Current Velocity", encoder.getVelocity());
    }

    public void stop() {
        intakeRollerMotor.stopMotor();
        SmartDashboard.putNumber("IntakeRollers/Current Velocity", 0.0);
    }

}
