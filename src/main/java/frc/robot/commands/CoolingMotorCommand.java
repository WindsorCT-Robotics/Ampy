package frc.robot.commands;

import frc.robot.subsystems.CoolingMotorSubsystem;

public class CoolingMotorCommand {
    
    private final CoolingMotorSubsystem solenoid;

    public CoolingMotorCommand(CoolingMotorSubsystem coolingSolenoid){
        this.solenoid = coolingSolenoid;
    }

    public void execute(){
        solenoid.enable();
    }

    public void end(){
        solenoid.disable();
    }
}
