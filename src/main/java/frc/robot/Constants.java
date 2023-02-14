package frc.robot;

import java.util.function.DoubleSupplier;

import frc.robot.commands.ForwardConveyorCommand;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be
 * declared globally (i.e. public static). Do not put anything functional in
 * this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public class Constants {

    public static final class DriveConstants {
        public static final double DRIVE_FULL_SPEED = 0.8; // 0.8 is an arbitrary value picked by Evan(foot 4)
        public static final double TURN_RADIUS = 0.4; // 0.4 is the best arbitrary value picked by David McManus (6 foot
                                                      // 8)

    }

    public static final class ConveyorMotorConstants {
        public static final int CAN_ID = 5;
        public static final double TARGET_FORWARD_RPM = 250; //250 is not an arbitrary number
        public static final double TARGET_REVERSE_RPM = -200; //-200 is an arbitrary number chosen by Evan
        public static final double GEAR_RATIO = 20;
        public static final double MAX_RPM = 5700;
        public static final double MAX_POWER_OUTPUT = 1; // Max power output out of 100%
        public static final double MIN_POWER_OUTPUT = -1; // Min power output out of 100%
        public static final double kP = 6e-5;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kIz = 0;
        public static final double kFF = 0.000015;
    }

    public static final class ControlConstants {
        public static final double CONVEYOR_TRIGGER_THRESHOLD = 0.3;
    }

    public static final class DigitalConstants {
        public static final int DIGITAL_FRONT_INPUT = 4;
        public static final int DIGITAL_BACK_INPUT = 5;
    }

    public static final class TimeConstants {
        public static final double RAMP_TIME = 0.4;
    }

    public static final class PneumaticsConstants {
        public static final int pcmCANID = 20;
        public static final int armSolenoidChannel = 1;
    }
}