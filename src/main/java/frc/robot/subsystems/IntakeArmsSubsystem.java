
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class IntakeArmsSubsystem extends SubsystemBase {

    private Solenoid armSolenoid;
    private boolean extended;
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
    public void extend() {
        armSolenoid.set(false);
        extended = true;
    }

    /**
     * Raises the intake of the robot (RETRACT THE SOLENOID TO RAISE THE ARM)
     */
    public void retract() {
        armSolenoid.set(true);
        extended = false;
    }

    public boolean isExtended() {
        return extended;
    }

    @Override
    public void periodic() {

        SmartDashboard.putBoolean(this.getClass().getName() + " .isExtended()", extended);
    }

    @Override
    public void simulationPeriodic() {

    }

}
