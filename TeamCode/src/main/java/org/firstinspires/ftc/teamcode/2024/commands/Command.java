package org.firstinspires.ftc.teamcode.commands;

import java.util.ArrayList;
import java.util.List;
import org.firstinspires.ftc.teamcode.Subsystem;

public abstract class Command {
    private boolean finished;
    private boolean started;
    private List<Subsystem> subsystems;
    public Command(){
        subsystems = new ArrayList<Subsystem>();
    }
    public void registerSubsystem(Subsystem s){
        subsystems.add(s);
    }
    
    public void begin(){
        started = true;
        finished  = false;
        for(Subsystem s : subsystems){
            s.setBusy(this);
        }
        beginImpl();
    }
    public void loop(){
        if(!started) return;
        if (finished) return;
        loopImpl();
    }
    public void interrupt(){
        interruptImpl();
        finish();
    }
    public void finish(){
        finished = true;
        finishImpl();
        for(Subsystem s : subsystems) s.releaseBusy();
    }
    public boolean isStarted(){
        return started;
    }
    public boolean isFinished(){
        return finished;
    }
    public abstract void beginImpl();
    public abstract void loopImpl();
    public void finishImpl(){
        
    }
    public void interruptImpl(){
        
    }
}
