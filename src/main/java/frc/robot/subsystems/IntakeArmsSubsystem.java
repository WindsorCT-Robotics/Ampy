
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class IntakeArmsSubsystem extends SubsystemBase {

    public enum ArmState {
        RAISED,
        LOWERED
    }

    private Solenoid armSolenoid;
    private static ArmState currentArmState = ArmState.RAISED;
    public static final int armSolenoidChannel = 0;
    public static final int pcmCANID = 20;

    public IntakeArmsSubsystem() {
        armSolenoid = new Solenoid(IntakeArmsSubsystem.pcmCANID, PneumaticsModuleType.CTREPCM,
                IntakeArmsSubsystem.armSolenoidChannel);
        addChild("ArmSolenoid", armSolenoid);

    }

    /**
     * Raises / lowers the robot's intake
     * 
     * @param state The desired arm state (raised or lowered)
     */
    public void setArmState(ArmState state) {
        armSolenoid.set(state == ArmState.LOWERED);
        currentArmState = state;
    }

    /**
     * Returns the intake's raised/lowered state
     * 
     * @return The current arm state
     */
    public ArmState getArmState() {
        return currentArmState;
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Arms Raised?", (currentArmState == ArmState.RAISED) ? true : false);
    }

}
