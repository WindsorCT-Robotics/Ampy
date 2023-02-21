package frc.robot;

import java.util.function.DoubleSupplier;

import frc.robot.commands.TurnConveyorCommand;

public class Constants {

    public static final class DriveConstants {
        public static final double DRIVE_FULL_SPEED = 0.8; // 0.8 is an arbitrary value picked by Evan(foot 4)
        public static final double TURN_RADIUS = 0.4; // 0.4 is the best arbitrary value picked by David McManus (6 foot
                                                      // 8)

    }

    public static final class MotorConstants {
        public static final int INTAKE_MOTOR = 9;
        public static final int kLeftIndexMotor = 5;
        public static final int kRightIndexMotor = 18;
        public static final int kShooterMotor = 6;
        public static final double CONVEYOR_FULL_SPEED = 0.8; // 0.8 is an arbitrary value picked by Evan
    }

    public static final class ControlConstants {
        public static final double SHOOT_TRIGGER_THRESHOLD = 0.3;
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