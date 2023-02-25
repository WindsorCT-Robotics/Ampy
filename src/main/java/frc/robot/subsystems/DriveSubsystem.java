
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.DriveSubsystem;

import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class DriveSubsystem extends SubsystemBase {
    private WPI_TalonFX leftMaster;
    private WPI_TalonFX leftFollower;
    private WPI_TalonFX rightMaster;
    private WPI_TalonFX rightFollower;
    private DifferentialDrive drive;

    public DriveSubsystem() {
        leftMaster = initMotor("Left Master", 1);
        leftFollower = initMotor("Left Follower", 2);

        leftMaster.setInverted(TalonFXInvertType.CounterClockwise);
        leftFollower.setInverted(TalonFXInvertType.FollowMaster);
        leftFollower.follow(leftMaster);

        rightMaster = initMotor("Right Master", 3);
        rightFollower = initMotor("Right Follower", 4);

        rightMaster.setInverted(TalonFXInvertType.CounterClockwise);
        rightFollower.setInverted(TalonFXInvertType.FollowMaster);
        rightFollower.follow(leftMaster);

        drive = new DifferentialDrive(leftMaster, rightMaster);

    }

    /**
     * Drive the robot
     * 
     * @param speed    Forward speed in [-1, 1]
     * @param rotation Curvature in [-1, 1]
     */
    public void drive(double speed, double rotation) {
        drive.curvatureDrive(speed, rotation, true);
    }

    private WPI_TalonFX initMotor(String name, int canId) {
        WPI_TalonFX motor = new WPI_TalonFX(canId);
        addChild(name, motor);
        return motor;
    }

}
