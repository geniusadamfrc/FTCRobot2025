package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystem.CommandSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Subsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Command {
    private boolean finished;
    private boolean started;
    private List<CommandSubsystem> subsystems;
    public Command()
    {
        subsystems = new ArrayList<CommandSubsystem>();

    }

    public void registerCommandSubsystem(CommandSubsystem s){
        subsystems.add(s);
    }
    public void registerBasicSubsystem(Subsystem s){
    }
    public List<CommandSubsystem> getSubsystems() { return subsystems;
    }
    public boolean isStarted(){
        return started;
    }
    public boolean isFinished(){
        return finished;
    }



    public void begin(){
        started = true;
        finished  = false;
        beginImpl();
    }
    public abstract void beginImpl();

    public void loop(){
        if(!started) return;
        if (finished) return;
        loopImpl();
    }
    public abstract void loopImpl();
    public void interrupt(){
        interruptImpl();
        finish();
    }
    public void interruptImpl(){

    }
    public void finish(){
        finished = true;
        finishImpl();

    }



    public void finishImpl(){
        
    }



}
