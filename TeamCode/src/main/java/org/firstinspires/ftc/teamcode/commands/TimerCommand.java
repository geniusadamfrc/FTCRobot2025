package org.firstinspires.ftc.teamcode.commands;


public class TimerCommand extends Command {
    private long startTime;
    private long runTime; 
    public TimerCommand(long timeInMilliseconds){
        this.runTime = timeInMilliseconds;
    }
    public void beginImpl(){
        startTime = System.currentTimeMillis();
    }
    public void loopImpl(){
        if (startTime + runTime < System.currentTimeMillis()){
            finish();
        }
    }
    
}
