
package frc.robot.subsystems;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants.PneumaticsConstants;

public class IntakeArms extends SubsystemBase {

    private Solenoid armSolenoid;
    private boolean extended;

    public IntakeArms() {

        armSolenoid = new Solenoid(PneumaticsConstants.pcmCANID, PneumaticsModuleType.CTREPCM,
                PneumaticsConstants.armSolenoidChannel);
        addChild("ArmSolenoid", armSolenoid);

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

    /**
     * 
     * @return Returns the state of the arms (extended/retracted)
     */
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
