package frc.robot.commands.drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

/**
 * Command to drive the robot, using speed and rotation values. Requires the
 * DriveSubsystem.
 */
public class DriveCommand extends CommandBase {
    // Speed values
    private DoubleSupplier speed;
    // Value to scale joystick speed input by. Should probably be less than 1.
    private double speedScale = 1;
    private DoubleSupplier manualSpeedScale;
    // SlewRateLimiter and values to smooth acceleration
    double positiveSpeedRateLimit = 5.0;
    double negativeSpeedRateLimit = -5.0;
    private SlewRateLimiter speedLimiter = new SlewRateLimiter(positiveSpeedRateLimit, negativeSpeedRateLimit, 0);

    // Rotation values
    private DoubleSupplier turn;
    // Value to scale joystick rotation input by. Should probably be less than 1.
    private double turnScale = 0.5;
    private DoubleSupplier manualTurnScale;
    // SlewRateLimiter and values to smooth acceleration
    double positiveTurnRateLimit = 10.0;
    double negativeTurnRateLimit = -10.0;
    private SlewRateLimiter turnLimiter = new SlewRateLimiter(positiveTurnRateLimit, negativeTurnRateLimit, 0);

    // Controls whether inputs are squared.
    private boolean squareInputs = true;

    private DriveSubsystem drivetrainSubsystem;

    /**
     * Command to drive the robot, using speed and rotation values.
     * 
     * @param speed               Joystick speed in [-1.0, 1.0]
     * @param turn                Joystick rotation in [-1.0, 1.0]
     * @param drivetrainSubsystem The DriveSubsystem
     */
    public DriveCommand(DoubleSupplier speed, DoubleSupplier manualSpeedScale, DoubleSupplier turn, DoubleSupplier manualTurnScale, DriveSubsystem drivetrainSubsystem) {
        this.speed = speed;
        this.manualSpeedScale = manualSpeedScale;
        
        this.turn = turn;
        this.manualTurnScale = manualTurnScale;
        
        this.drivetrainSubsystem = drivetrainSubsystem;
        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void initialize() {
        drivetrainSubsystem.stop();
        SmartDashboard.putNumber("Speed Scale", speedScale);
        SmartDashboard.putNumber("Turn Scale", turnScale);
        SmartDashboard.putBoolean("Square Inputs", squareInputs);

        SmartDashboard.putNumber("Positive Speed Rate Limit", positiveSpeedRateLimit);
        SmartDashboard.putNumber("Negative Speed Rate Limit", negativeSpeedRateLimit);
        SmartDashboard.putNumber("Positive Turn Rate Limit", positiveTurnRateLimit);
        SmartDashboard.putNumber("Negative Turn Rate Limit", negativeTurnRateLimit);

    }

    @Override
    public void execute() {
        // Update control values
        speedScale = SmartDashboard.getNumber("Speed Scale", speedScale) * manualSpeedScale.getAsDouble();
        turnScale = SmartDashboard.getNumber("Turn Scale", turnScale) * manualTurnScale.getAsDouble();
        squareInputs = SmartDashboard.getBoolean("Square Inputs", squareInputs);

        // Calculate speed values
        double driveSpeed = speed.getAsDouble() * speedScale * (squareInputs ? Math.abs(speed.getAsDouble()) : 1);
        double turnSpeed = turn.getAsDouble() * turnScale * (squareInputs ? Math.abs(turn.getAsDouble()) : 1);

        // Update limiters
        double newPositiveSpeedRateLimit = SmartDashboard.getNumber("Positive Speed Rate Limit",
                positiveSpeedRateLimit);
        double newNegativeSpeedRateLimit = SmartDashboard.getNumber("Negative Speed Rate Limit",
                negativeSpeedRateLimit);
        double newPositiveTurnRateLimit = SmartDashboard.getNumber("Positive Turn Rate Limit", positiveTurnRateLimit);
        double newNegativeTurnRateLimit = SmartDashboard.getNumber("Negative Turn Rate Limit", negativeTurnRateLimit);

        if (newPositiveSpeedRateLimit != positiveSpeedRateLimit
                || newNegativeSpeedRateLimit != negativeSpeedRateLimit) {
            positiveSpeedRateLimit = newPositiveSpeedRateLimit;
            negativeSpeedRateLimit = newNegativeSpeedRateLimit;
            speedLimiter = new SlewRateLimiter(positiveSpeedRateLimit, negativeSpeedRateLimit, driveSpeed);
        }

        if (newPositiveTurnRateLimit != positiveTurnRateLimit || newNegativeTurnRateLimit != negativeTurnRateLimit) {
            positiveTurnRateLimit = newPositiveTurnRateLimit;
            negativeTurnRateLimit = newNegativeTurnRateLimit;
            turnLimiter = new SlewRateLimiter(positiveTurnRateLimit, negativeTurnRateLimit, turnSpeed);
        }

        // Drive, applying SlewRateLimiter to smooth out values
        drivetrainSubsystem.drive(speedLimiter.calculate(driveSpeed), turnLimiter.calculate(turnSpeed));
    }

    @Override
    public void end(boolean interrupted) {
        drivetrainSubsystem.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
