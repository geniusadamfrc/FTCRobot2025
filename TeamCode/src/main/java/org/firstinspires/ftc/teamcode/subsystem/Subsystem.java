package org.firstinspires.ftc.teamcode.subsystem;
import org.firstinspires.ftc.teamcode.commands.Command;

public class Subsystem {
    protected Command activeCommand;
    public boolean isBusy(){
        return activeCommand!=null && activeCommand.isStarted() && !activeCommand.isFinished();
    }
    public void playOnce(){

    }

    public void setBusy(Command command){
        activeCommand = command;
    }
    public void releaseBusy(){
        activeCommand = null;
    }
    public void forceExit() {
        activeCommand.interrupt();
        releaseBusy();
    }
    public void loop(){}
}