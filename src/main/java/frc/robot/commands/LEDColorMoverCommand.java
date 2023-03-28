package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LEDSubsystem;

public class LEDColorMoverCommand extends CommandBase {
    LEDSubsystem led;
    private int counter = 0;
    private int wavelength;
    private Color first;
    private Color second;
    private int delayCounter = 0;

    public LEDColorMoverCommand(LEDSubsystem led, Color first, Color second, int length) {
        this.led = led;
        this.first = first;
        this.second = second;
        this.wavelength = length;
        addRequirements(led);
    }

    @Override
    public void initialize() {
        boolean isFirst = true;
        for (int x = 0; x < led.ledBufferLength(); x += wavelength) {
            for (int y = x; y < x + wavelength; y++) {
                if (isFirst) {
                    led.setColor(y, first);
                } else {
                    led.setColor(y, second);
                }
            }
            isFirst = !isFirst;
        }

        led.setData();
    }
    // public void initialize() {
    // boolean isFirst = false;
    // for (int x = 0; x < led.ledBufferLength(); x += led.ledBufferLength() /
    // wavelength) {
    // for (int y = x; y < led.ledBufferLength() / wavelength || y <
    // led.ledBufferLength(); y++) {
    // if (isFirst) {
    // led.setColor(y, first);
    // } else {
    // led.setColor(y, second);
    // }
    // }
    // isFirst = !isFirst;
    // }

    // led.setData();
    // }

    // @Override
    // public void execute() {
    // for (int i = 0; i < led.ledBufferLength() - 1; i += wavelength) {
    // if (led.getLED(i + counter).equals(second)) {
    // led.setColor(i + counter, first);
    // } else {
    // led.setColor(i + counter, second);
    // }
    // }
    // led.setData();
    // counter += 1;
    // if (counter == wavelength) {
    // counter = 0;
    // }
    // }

    @Override
    public void execute() {
        if (delayCounter == 2) {

            boolean isFirst = true;
            for (int x = 0; x < led.ledBufferLength() - 1; x += wavelength) {
                for (int y = x + counter; y < x + wavelength; y++) {
                    if (isFirst) {
                        led.setColor(y, first);
                    } else {
                        led.setColor(y, second);
                    }
                }
                isFirst = !isFirst;

                led.setData();

                counter += 1;
                if (counter == wavelength) {
                    counter = 0;
                }
            }
            delayCounter = 0;
        }
        delayCounter += 1;
    }
}