package org.firstinspires.ftc.teamcode.commands.simple.ramp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystem.Ramp;

public class PushBallUp  extends Command {
    private Ramp ramp;
    private double rampPosition;


    public PushBallUp(){
        this.ramp = Robot.ramp;
    }
    @Override
    public void beginImpl() {
        ramp.setRampPower(0.5);
    }

    @Override
    public void loopImpl() {

    }
    @Override
    public void finishImpl(){
        ramp.setRampPower(0.0);
    }
}
