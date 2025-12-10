package org.firstinspires.ftc.teamcode.commands.simple.ramp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystem.Ramp;

public class SlowRampUp extends Command {
    private double power;
    public SlowRampUp() {

    }

    @Override
    public void beginImpl() {
        Robot.ramp.setRampPower(-0.2);
        }

    @Override
    public void loopImpl() {
        Robot.ramp.setRampPower(-0.2);
    }

    @Override
    public void finishImpl() {
        Robot.ramp.setRampPower(0.0);
    }
}
