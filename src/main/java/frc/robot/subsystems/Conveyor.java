
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Conveyor extends SubsystemBase {
    private static Conveyor conveyor;

    private String name = "Index";
    private WPI_TalonFX conveyorMotor;
    private DigitalInput frontInput;
    private DigitalInput backInput;

    public static final int DIGITAL_BACK_INPUT = 5;

    public static final int DIGITAL_FRONT_INPUT = 4;

    public static final double CONVEYOR_FULL_SPEED = 0.8; // 0.8 is an arbitrary value picked by Evan

    public static final int kLeftIndexMotor = 5;

    public Conveyor() {
        conveyorMotor = new WPI_TalonFX(Conveyor.kLeftIndexMotor);
        addChild("IndexMotor", conveyorMotor);
        conveyorMotor.setInverted(false);
        conveyorMotor.setNeutralMode(NeutralMode.Brake);

        frontInput = new DigitalInput(Conveyor.DIGITAL_FRONT_INPUT);
        backInput = new DigitalInput(Conveyor.DIGITAL_BACK_INPUT);
    }

    public static synchronized Conveyor getInstance() {
        if (conveyor == null) {
            conveyor = new Conveyor();
        }
        return conveyor;
    }

    @Override
    public void periodic() {
    }

    @Override
    public void simulationPeriodic() {
    }

    public void outputTelemetry() {
        SmartDashboard.putBoolean(getName() + "/Front Input", frontInput.get());
        SmartDashboard.putBoolean(getName() + "/Back Input", backInput.get());

    }

    @Override
    public String getName() {
        return name;
    }

    public void stop() {
        conveyorMotor.stopMotor();

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public boolean getFrontInput() {
        return !frontInput.get();
    }

    public boolean getBackInput() {
        return !backInput.get();
    }

    public void turn() {
        conveyorMotor.set(0.5);
    }

    public void reverse() {
        conveyorMotor.set(-0.5);
    }
}