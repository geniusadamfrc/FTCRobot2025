package org.firstinspires.ftc.teamcode.commands.simple.ramp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;

public class TurnOnIntake extends Command {
    public TurnOnIntake( double rampPower){
        registerBasicSubsystem(Robot.ramp);
    }
    @Override
    public void beginImpl() {
        Robot.intake.setIntaking();
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
