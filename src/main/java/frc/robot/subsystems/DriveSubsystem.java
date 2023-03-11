package frc.robot.subsystems;

import java.lang.Math;
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
    WPI_TalonFX leftMain;
    WPI_TalonFX leftFollower;
    WPI_TalonFX rightMain;
    WPI_TalonFX rightFollower;

    // DifferentialDrive object for drive calculations
    DifferentialDrive drive;

    // Current neutral mode
    NeutralMode neutralMode = NeutralMode.Coast;

    public DriveSubsystem() {
        // Motor initialization
        // Left motors turn clockwise
        leftMain = initMotor(1);
        leftMain.setInverted(TalonFXInvertType.Clockwise);
        leftFollower = initMotor(2);
        leftFollower.follow(leftMain);
        leftFollower.setInverted(TalonFXInvertType.FollowMaster);

        // Right motors turn counterclockwise
        rightMain = initMotor(3);
        rightMain.setInverted(TalonFXInvertType.CounterClockwise);
        rightFollower = initMotor(4);
        rightFollower.follow(rightMain);
        rightFollower.setInverted(TalonFXInvertType.FollowMaster);

        // Drivetrain initialization
        drive = new DifferentialDrive(leftMain, rightMain);
    }

    /**
     * Helper method to initialize a WPI_TalonFX.
     * 
     * @param canId The motor's CAN ID
     * @return newly initialized WPI_TalonFX
     */
    private WPI_TalonFX initMotor(int canId) {
        WPI_TalonFX motor = new WPI_TalonFX(canId);
        motor.configFactoryDefault();
        motor.setNeutralMode(neutralMode);
        return motor;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left main sensor position", leftMain.getSelectedSensorPosition());
        SmartDashboard.putNumber("Left main sensor velocity", leftMain.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Right main sensor position", rightMain.getSelectedSensorPosition());
        SmartDashboard.putNumber("Right main sensor velocity", rightMain.getSelectedSensorVelocity());
        // Motor temps
        SmartDashboard.putNumber("Left main motor", Math.round(leftMain.getTemperature()));
        SmartDashboard.putNumber("Left follower motor", Math.round(leftFollower.getTemperature()));
        SmartDashboard.putNumber("Right main motor", Math.round(rightMain.getTemperature()));
        SmartDashboard.putNumber("Right follower motor", Math.round(rightFollower.getTemperature()));
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
        this.neutralMode = neutralMode;
        leftMain.setNeutralMode(neutralMode);
        leftFollower.setNeutralMode(neutralMode);
        rightMain.setNeutralMode(neutralMode);
        rightFollower.setNeutralMode(neutralMode);
    }

    /**
     * Get the drivetrain's current neutral mode
     * 
     * @return the current neutral mode
     */
    public NeutralMode getNeutralMode() {
        return neutralMode;
    }

    /**
     * Stop the subsystem
     */
    public void stop() {
        leftMain.set(0);
        rightMain.set(0);
    }

}
