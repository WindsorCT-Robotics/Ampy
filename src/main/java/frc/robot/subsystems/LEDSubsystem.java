package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase {
    private static int ledPort = 0;
    private static int ledLength = 170;
    private AddressableLED led;
    private AddressableLEDBuffer ledBuffer;

    public LEDSubsystem() {
        led = new AddressableLED(ledPort);
        ledBuffer = new AddressableLEDBuffer(ledLength);
        led.setLength(ledBuffer.getLength());
        led.start();

        setLedColor(0, 0, 255);
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

    public boolean convertColor(){
        if (ledBuffer.getLED(0).toString().equals("#0000FF")){
        return true;
        }
        return false;
    }

}
