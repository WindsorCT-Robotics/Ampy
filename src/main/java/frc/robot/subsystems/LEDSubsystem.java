package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase {
    private static int ledPort = 1;
    private static int ledLength = 170;
    private AddressableLED led;
    private AddressableLEDBuffer ledBuffer;
    private static boolean isOrange;
    private int counter = 0;
    public static int ledCheck = 0;

    public LEDSubsystem() {
        led = new AddressableLED(ledPort);
        ledBuffer = new AddressableLEDBuffer(ledLength);
        led.setLength(ledBuffer.getLength());
        led.start();
        colorInitialization();
    }

    // Initializes the colors of our LEDs in alternating groups of 5 orange, 5
    // blue, and so on
    public void colorInitialization() {
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            if (i % 5 == 0) {
                if (i != 0 && i % 10 != 0) {
                    isOrange = false;
                } else {
                    isOrange = true;
                }
            }
            if (isOrange == false) {
                ledBuffer.setRGB(i, 0, 0, 255);
            } else {
                ledBuffer.setRGB(i, 255, 110, 0);

            }

        }
        led.setData(ledBuffer);
    }

    public void colorMover() {
        for (var i = 0; i < ledBuffer.getLength() - 1; i++) {
            if (i % 5 == 0) {
                if (ledBuffer.getLED(i + counter).toString().equals("#0000FF")) {
                    ledBuffer.setRGB(i + counter, 255, 50, 0);
                } else {
                    ledBuffer.setRGB(i + counter, 0, 0, 255);
                }
            }
        }
        led.setData(ledBuffer);
        counter += 1;
        if (counter == 5) {
            counter = 0;
        }
    }

    @Override
    public void periodic() {
        //Ensures the periodic doesn't run during TeleOp when other colors need to be displayed
        if (ledCheck == 0) {
            colorMover();
        }
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
