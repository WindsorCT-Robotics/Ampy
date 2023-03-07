package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class CoolingMotorSubsystem extends SubsystemBase {
    private final Solenoid coolingSolenoid;
    public static final int coolingSolenoidChannel = 6;
    public static final int pcmCANID = 11;

    public CoolingMotorSubsystem() {
        coolingSolenoid = new Solenoid(CoolingMotorSubsystem.pcmCANID, PneumaticsModuleType.CTREPCM,
                CoolingMotorSubsystem.coolingSolenoidChannel);
    }

    public void enable() {
        coolingSolenoid.set(true);
    }

    public void disable() {
        coolingSolenoid.set(false);
    }
}