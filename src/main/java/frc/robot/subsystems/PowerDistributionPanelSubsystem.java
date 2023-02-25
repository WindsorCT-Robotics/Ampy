package frc.robot.subsystems;
import java.lang.Math;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Interfaces.PdpDevice;
import frc.robot.subsystems.PowerDistributionPanelSubsystem;

public class PowerDistributionPanelSubsystem extends SubsystemBase implements PdpDevice {
    private final PowerDistribution powerDistributionPanel;

    public PowerDistributionPanelSubsystem(PowerDistribution powerDistributionPanel){
        this.powerDistributionPanel = powerDistributionPanel;
        addChild("PDP", powerDistributionPanel);
    }
        @Override
        public void periodic()
        {
            for (int i = 0; i <= 15; i++) 
            {
                SmartDashboard.putNumber("ID " + i, this.powerDistributionPanel.getCurrent(i));
            }
            SmartDashboard.putNumber("Temperature (C)", Math.round(this.powerDistributionPanel.getTemperature()));
            SmartDashboard.putNumber("Total Current", Math.round(this.powerDistributionPanel.getTotalCurrent()));
            SmartDashboard.putNumber("Total Power (W)", this.powerDistributionPanel.getTotalPower());
            SmartDashboard.putNumber("Total Energy (J)",this.powerDistributionPanel.getTotalEnergy());
        }
        // Overide method signature?
        // get channel current amperage for master device and each follower device
        // compare each follower device amperage with master device
        // if any device is out-of-tolerance, throw new MismatchedAmperageException.

    }