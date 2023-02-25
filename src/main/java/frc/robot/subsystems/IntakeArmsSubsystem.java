
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class IntakeArmsSubsystem extends SubsystemBase {

    private Solenoid armSolenoid;
    private static boolean raisedState = true;
    public static final int armSolenoidChannel = 1;
    public static final int pcmCANID = 20;
    private static IntakeArmsSubsystem intakeArms;

    private IntakeArmsSubsystem() {

        armSolenoid = new Solenoid(IntakeArmsSubsystem.pcmCANID, PneumaticsModuleType.CTREPCM,
                IntakeArmsSubsystem.armSolenoidChannel);
        addChild("ArmSolenoid", armSolenoid);

    }

    public static synchronized IntakeArmsSubsystem getInstance() {
        if (null == intakeArms) {
            intakeArms = new IntakeArmsSubsystem();
        }
        return intakeArms;
    }

    /**
     * Lowers the intake of the robot (EXTEND THE SOLENOID TO LOWER THE ARMS)
     */
    public void lowerArms() {
        armSolenoid.set(true);
        raisedState = false;
    }

    /**
     * Raises the intake of the robot (RETRACT THE SOLENOID TO RAISE THE ARM)
     */
    public void raiseArms() {
        armSolenoid.set(false);
        raisedState = true;
    }

    public boolean getRaisedState() {
        return raisedState;
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Arms Raised?", raisedState);
    }

    @Override
    public void simulationPeriodic() {

    }

}
