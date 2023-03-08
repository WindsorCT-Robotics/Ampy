package frc.robot.subsystems;

import java.lang.Math;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PowerDistributionPanelSubsystem extends SubsystemBase {
    private final PowerDistribution powerDistributionPanel;

    public PowerDistributionPanelSubsystem(PowerDistribution powerDistributionPanel) {
        this.powerDistributionPanel = powerDistributionPanel;
        addChild("PDP", powerDistributionPanel);
    }

    @Override
    public void periodic() {
        //Each subsystem will be given an id in each subsystem class
        //Channels are numbered 0 to 15 because there are 16 channels on the PDP
        for (int i = 0; i <= 15; i++) {
            SmartDashboard.putNumber("ID " + i, this.powerDistributionPanel.getCurrent(i));
        }
        SmartDashboard.putNumber("PDP/Temperature (C)", Math.round(this.powerDistributionPanel.getTemperature()));
        SmartDashboard.putNumber("PDP/Total Current", Math.round(this.powerDistributionPanel.getTotalCurrent()));
        SmartDashboard.putNumber("PDP/Total Power (W)", this.powerDistributionPanel.getTotalPower());
        SmartDashboard.putNumber("PDP/Total Energy (J)", this.powerDistributionPanel.getTotalEnergy());
    }

}