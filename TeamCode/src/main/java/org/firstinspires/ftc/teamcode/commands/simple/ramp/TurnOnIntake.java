package org.firstinspires.ftc.teamcode.commands.simple.ramp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;

public class TurnOnIntake extends Command {
    private static final double RAMP_POWER = 0.25;
    private double rampPower;
    public TurnOnIntake( double rampPower){
        registerBasicSubsystem(Robot.ramp);
        this.rampPower =rampPower;
    }
    @Override
    public void beginImpl() {
        Robot.intake.setIntaking();
        Robot.ramp.setTargetPower(RAMP_POWER);
        Robot.ramp.setSlowRamp();
    }

    @Override
    public void loopImpl() {
    }
    @Override
    public void finishImpl(){
        Robot.ramp.setIdleRamp();
        Robot.intake.setIdleIntake();
    }
}
