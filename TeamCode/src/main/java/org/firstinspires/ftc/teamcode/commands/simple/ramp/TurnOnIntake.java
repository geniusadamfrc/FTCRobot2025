package org.firstinspires.ftc.teamcode.commands.simple.ramp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;

public class TurnOnIntake extends Command {

    private double power;
    private double rampPower;
    public TurnOnIntake(double power, double rampPower){
        registerSubsystem(Robot.ramp);
        this.power = power;
        this.rampPower =rampPower;
    }
    @Override
    public void beginImpl() {
        Robot.ramp.setIntakePower(this.power);
        Robot.ramp.setRampPower(this.rampPower);
    }

    @Override
    public void loopImpl() {
        Robot.ramp.setIntakePower(this.power);
        Robot.ramp.setRampPower(this.rampPower);
    }
    @Override
    public void finishImpl(){
        Robot.ramp.setIntakePower(0.0);
        Robot.ramp.setRampPower(0.0);
    }
}
