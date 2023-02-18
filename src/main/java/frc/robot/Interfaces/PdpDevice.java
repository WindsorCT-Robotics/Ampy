package frc.robot.Interfaces;

import java.util.Set;

import frc.robot.Types.Amperage;
import frc.robot.Types.Channel;

public interface PdpDevice extends NamedDevice {
    Amperage getMinimumAmpage();
    Amperage getMaximumAmpage();
    Channel getChannel();
    Set<PdpDevice> getFollowerDevices();
}
