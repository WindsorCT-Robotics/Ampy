package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LEDSubsystem;

public class SetLedColorCommand extends CommandBase {
    LEDSubsystem led;
    private int red;
    private int green;
    private int blue;

    public SetLedColorCommand(LEDSubsystem led, int red, int green, int blue) {
        this.led = led;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public void execute() {
        led.setLedColor(red, green, blue);
    }

    public void end() {
        led.setLedColor(0, 0, 0);
    }
}
