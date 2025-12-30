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
        Robot.intake.setSlowIntaking();
        Robot.ramp.setTargetPower(0.5);
        Robot.ramp.setFeeding();
        rampPosition = Robot.ramp.getRampPosition();
    }

    @Override
    public void loopImpl() {
    }
    @Override
    public void finishImpl() {
        Robot.ramp.setIdleRamp();
        Robot.intake.setIdleIntake();
    }

    public boolean hasMovedEnough(){
        return rampPosition - MINIMUM_RAMP_MOVEMENT > ramp.getRampPosition();
    }
    public boolean hasMovedMaximum(){
        return rampPosition - MAXIMUM_RAMP_MOVEMENT > ramp.getRampPosition();
    }
}
