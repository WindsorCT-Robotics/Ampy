package frc.robot;

import java.util.function.DoubleSupplier;

import frc.robot.commands.TurnConveyorCommand;

public class Constants {

    public static final class DriveConstants {
        public static final double DRIVE_FULL_SPEED = 0.8; // 0.8 is an arbitrary value picked by Evan(foot 4)
        public static final double TURN_RADIUS = 0.4; // 0.4 is the best arbitrary value picked by David McManus (6 foot
                                                      // 8)

    }

    public static final class ConveyorMotorConstants {
        public static final int CAN_ID = 5; //CAN ID should be 5
        public static final double TARGET_FORWARD_RPM = 250; //250 is not an arbitrary number
        public static final double TARGET_REVERSE_RPM = -200; //-200 is an arbitrary number chosen by Evan
        public static final double GEAR_RATIO = 20;
        public static final double MAX_RPM = 2000;
        public static final double MAX_POWER_OUTPUT = 1; // Max power output out of 100%
        public static final double MIN_POWER_OUTPUT = -1; // Min power output out of 100%
        public static final double kP = 0.0001;
        public static final double kI = 0.000001;
        public static final double kD = 0.00002;
        public static final double kIz = 0;
        public static final double kFF = 0.000015;
    }

    public static final class IntakeRollersMotorConstants {
        public static final int CAN_ID = 6; //CAN ID should be 6
        public static final double TARGET_FORWARD_RPM = 250; //250 is not an arbitrary number
        public static final double TARGET_REVERSE_RPM = -200; //-200 is an arbitrary number chosen by Evan
        public static final double GEAR_RATIO = 20;
        public static final double MAX_RPM = 2000;
        public static final double MAX_POWER_OUTPUT = 1; // Max power output out of 100%
        public static final double MIN_POWER_OUTPUT = -1; // Min power output out of 100%
        public static final double kP = 0.0001;
        public static final double kI = 0.000001;
        public static final double kD = 0.00002;
        public static final double kIz = 0;
        public static final double kFF = 0.000015;
    }

    public static final class DigitalConstants {
        public static final int conveyorFullSensor = 0;
    }

    public static final class TimeConstants {
        public static final double RAMP_TIME = 0.4;
    }

    public static final class PneumaticsConstants {
        public static final int pcmCANID = 20;
        public static final int armSolenoidChannel = 1;
    }
}