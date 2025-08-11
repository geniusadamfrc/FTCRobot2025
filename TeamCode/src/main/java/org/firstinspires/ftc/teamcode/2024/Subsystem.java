package org.firstinspires.ftc.teamcode;
import org.firstinspires.ftc.teamcode.commands.Command;

public class Subsystem {
    protected SubsystemState state = SubsystemState.IDLE;
    protected Command activeCommand;
    public boolean isBusy(){
        return state != SubsystemState.IDLE;
    }
    public boolean isAvailable(){
        return state == SubsystemState.IDLE;
    }
    public void setBusy(Command command){
        state = SubsystemState.COMMANDED;
        activeCommand = command;
    }
    public void releaseBusy(){
        state = SubsystemState.IDLE;
        activeCommand = null;
    }
    public void forceExit(){
        activeCommand.finish();
        releaseBusy();
    }
    public String getState(){
        return state.toString();
    }
    
    protected enum SubsystemState {
        IDLE, COMMANDED
    }
}