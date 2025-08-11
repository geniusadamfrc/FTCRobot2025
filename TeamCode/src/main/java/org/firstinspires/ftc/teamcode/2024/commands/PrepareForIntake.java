package org.firstinspires.ftc.teamcode.commands;
import org.firstinspires.ftc.teamcode.*;
public class PrepareForIntake extends Command{
    private SlideClaw claw;
    private TimerCommand timer;
    private final static int DELAY_TIME = 500; //in ms
    
    public PrepareForIntake(SlideClaw claw){
        this.claw = claw;
        registerSubsystem(claw);
    }
    public void beginImpl(){
        claw.raiseClaw();
        claw.openClaw();
        timer = new TimerCommand(DELAY_TIME);
        timer.begin();
    }
    public void loopImpl(){
        if (timer.isFinished())
            finish();
        else 
            timer.loop();
    }

    
    // todo: write your code here
}