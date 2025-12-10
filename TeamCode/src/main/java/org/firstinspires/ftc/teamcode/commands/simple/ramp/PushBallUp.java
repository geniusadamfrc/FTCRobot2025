package org.firstinspires.ftc.teamcode.commands.simple.ramp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystem.Ramp;

public class PushBallUp  extends Command {
    private Ramp ramp;
    private double rampPosition;
    private final static double MINIMUM_RAMP_MOVEMENT = 800;
    private final static double MAXIMUM_RAMP_MOVEMENT = 2500;

    public PushBallUp(){
        this.ramp = Robot.ramp;
    }
    @Override
    public void beginImpl() {
        ramp.setRampPower(-0.5);
        ramp.setIntakePower((-0.5));
        rampPosition = Robot.ramp.getRampPosition();
    }

    @Override
    public void loopImpl() {
        ramp.setRampPower(-0.5);
        ramp.setIntakePower(-0.8);
    }
    @Override
    public void finishImpl(){
        ramp.setRampPower(0.0);
    }

    public boolean hasMovedEnough(){
        return rampPosition - MINIMUM_RAMP_MOVEMENT > ramp.getRampPosition();
    }
    public boolean hasMovedMaximum(){
        return rampPosition - MAXIMUM_RAMP_MOVEMENT > ramp.getRampPosition();
    }
}
