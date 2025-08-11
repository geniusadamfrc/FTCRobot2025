package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.*;

public class CloseClaw extends Command{
    private ElevatorClaw claw;
    private Telemetry telemetry;
    private final int DELAY_TIME=300;
    private TimerCommand timer;
    public CloseClaw(ElevatorClaw claw, Telemetry telemetry){
        this.claw = claw;
        this.telemetry = telemetry;
    }
    
    public void beginImpl(){
        claw.closeClaw();
        timer = new TimerCommand(DELAY_TIME);
        timer.begin();
    }
    
    public void loopImpl(){
        if (timer.isFinished()){
            finish();
        }else{
            timer.loop();
        }
    }
    
    // todo: write your code here
}