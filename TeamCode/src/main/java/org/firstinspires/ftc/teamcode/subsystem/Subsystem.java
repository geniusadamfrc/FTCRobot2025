package org.firstinspires.ftc.teamcode.subsystem;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.CommandManager;

public class Subsystem {
    protected State state;
    protected Command currentCommand;

    public void playOnce(){
        state = State.IDLE;
        playOnceImpl();
    }
    public void playOnceImpl(){}
    public void loop(){}

    public boolean isActive(){
        return state ==State.ACTIVE;
    }
    public boolean isDefault(){
        return state == State.DEFAULT;
    }
    public boolean isIdle(){
        return state == State.IDLE;
    }
    public void setActive(Command command){
        currentCommand = command;
        state = State.ACTIVE;
    }
    public void setDefault(Command command){
        currentCommand = command;
        state = State.DEFAULT;
    }
    public void setIdle(){
        currentCommand = null;
        state = State.IDLE;
    }
    public void forceExit() {
        currentCommand.interrupt();
    }


    protected enum State {
        IDLE, DEFAULT, ACTIVE;
    }

}