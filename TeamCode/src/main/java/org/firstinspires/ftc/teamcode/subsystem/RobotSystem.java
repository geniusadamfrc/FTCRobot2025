package org.firstinspires.ftc.teamcode.subsystem;

public class RobotSystem extends Subsystem{
    private State state;


    public void setIdle(){

    }
    public void setShooting(){

    }
    public void setIntaking(){


    }
    public State getState(){
        return state;
    }
    public String getStateString(){
        return state.toString();
    }

    public enum State {
        IDLE, INTAKING, SHOOTING
    }
}
