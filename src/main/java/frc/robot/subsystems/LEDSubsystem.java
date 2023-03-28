package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase {
    private static int ledPort = 1;
    private static int ledLength = 170;
    private AddressableLED led;
    private AddressableLEDBuffer ledBuffer;
    public static int ledCheck = 0;

    public LEDSubsystem() {
        led = new AddressableLED(ledPort);
        ledBuffer = new AddressableLEDBuffer(ledLength);
        led.setLength(ledBuffer.getLength());
        led.start();

        // boolean isFirst = false;
        // for (int x = 0; x < ledBuffer.getLength(); x += wavelength) {
        // for (int y = x; y < ledBuffer.getLength() / wavelength || y <
        // ledBuffer.getLength(); y++) {
        // if (isFirst) {
        // led.setColor(y, first);
        // } else {
        // led.setColor(y, second);
        // }
        // }
        // isFirst = !isFirst;
        // }
        // led.setData(ledBuffer);
    }

    public void setColor(int index, Color color) {
        ledBuffer.setRGB(index, (int) (color.red * 255), (int) (color.green * 255), (int) (color.blue * 255));
    }

    public Color getLED(int index) {
        return ledBuffer.getLED(index);
    }

    public int ledBufferLength() {
        return ledBuffer.getLength();
    }

    public void setData() {
        led.setData(ledBuffer);
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("LED Color", convertColor());
    }

    public void setLedColor(int red, int green, int blue) {
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setRGB(i, red, green, blue);
        }
        led.setData(ledBuffer);
    }

    public boolean convertColor() {
        if (ledBuffer.getLED(0).toString().equals("#0000FF")) {
            return true;
        }
        return false;
    }

}
