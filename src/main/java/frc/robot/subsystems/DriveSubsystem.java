package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Subsystem to model the robot's drivetrain
 */
public class DriveSubsystem extends SubsystemBase {
    // Drive motors
    WPI_TalonFX leftMaster;
    WPI_TalonFX leftFollower;
    WPI_TalonFX rightMaster;
    WPI_TalonFX rightFollower;

    // DifferentialDrive object for drive calculations
    DifferentialDrive drive;

    public DriveSubsystem() {
        // Motor initialization
        // Left motors turn clockwise
        leftMaster = initMotor(1);
        leftMaster.setInverted(TalonFXInvertType.Clockwise);
        leftFollower = initMotor(2);
        leftFollower.follow(leftMaster);
        leftFollower.setInverted(TalonFXInvertType.FollowMaster);

        // Right motors turn counterclockwise
        rightMaster = initMotor(3);
        rightMaster.setInverted(TalonFXInvertType.CounterClockwise);
        rightFollower = initMotor(4);
        rightFollower.follow(rightMaster);
        rightFollower.setInverted(TalonFXInvertType.FollowMaster);

        // Drivetrain initialization
        drive = new DifferentialDrive(leftMaster, rightMaster);
    }

    /**
     * Helper method to initialize a WPI_TalonFX.
     * @param canId The motor's CAN ID
     * @return newly initialized WPI_TalonFX
     */
    private static WPI_TalonFX initMotor(int canId) {
        WPI_TalonFX motor = new WPI_TalonFX(canId);
        motor.configFactoryDefault();
        return motor;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left master sensor position", leftMaster.getSelectedSensorPosition());
        SmartDashboard.putNumber("Left master sensor velocity", leftMaster.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Right master sensor position", rightMaster.getSelectedSensorPosition());
        SmartDashboard.putNumber("Right master sensor velocity", rightMaster.getSelectedSensorVelocity());
    }

    /**
     * Drives the robot
     * 
     * @param speed Motor speed as a value in [-1.0, 1.0]
     * @param turn  The robot's curvature as a value in [-1.0, 1.0]. Also controls
     *              turn rate for turn-in-place maneuvers
     */
    public void drive(double speed, double turn) {
        drive.curvatureDrive(speed, turn, true);
    }

    /**
     * Set the drive motor's neutral mode
     * A motor's neutral mode determines whether it resists motion - brake mode - or
     * rotates freely - coast mode - when no power is applied to it.
     * 
     * @param neutralMode The motor's neutral mode
     */
    public void setNeutralMode(NeutralMode neutralMode) {
        leftMaster.setNeutralMode(neutralMode);
        leftFollower.setNeutralMode(neutralMode);
        rightMaster.setNeutralMode(neutralMode);
        rightFollower.setNeutralMode(neutralMode);
    }

    /**
     * Stop the subsystem
     */
    public void stop() {
        leftMaster.set(0);
        rightMaster.set(0);
    }

}