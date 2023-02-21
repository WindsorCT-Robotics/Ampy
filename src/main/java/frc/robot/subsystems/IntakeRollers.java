
package frc.robot.subsystems;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class IntakeRollers extends SubsystemBase {

    private Spark rollerMotor;

    public IntakeRollers() {

        rollerMotor = new Spark(0);
        addChild("RollerMotor", rollerMotor);
        rollerMotor.setInverted(false);

    }

    @Override
    public void periodic() {

    }

    @Override
    public void simulationPeriodic() {

    }

}
