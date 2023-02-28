package frc.robot.hardware;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * Subclass of CANSparkMax that implements Sendable, for SmartDashboard.
 */
public class WPI_CANSparkMax extends CANSparkMax implements Sendable {
    public WPI_CANSparkMax(int deviceID, MotorType type) {
        super(deviceID, type);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Motor Controller");
        builder.setActuator(true);
        builder.setSafeState(this::stopMotor);
        builder.addDoubleProperty("Value", this::get, this::set);
        
    }

}
