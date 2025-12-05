package org.firstinspires.ftc.teamcode.commands.simple.ramp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystem.Ramp;

public class SlowRampUp extends Command {
    private double rampPosition;
    private final static double MINIMUM_RAMP_MOVEMENT = 1500;
    private final static double MAXIMUM_RAMP_MOVEMENT = 3500;

    public SlowRampUp() {

    }

    @Override
    public void beginImpl() {
        Robot.ramp.setRampPower(-0.4);
        rampPosition = Robot.ramp.getRampPosition();
    }

    @Override
    public void loopImpl() {
        Robot.ramp.setRampPower(-0.4);

    }

    @Override
    public void finishImpl() {
        Robot.ramp.setRampPower(0.0);
    }
}
