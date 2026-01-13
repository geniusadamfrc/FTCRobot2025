package org.firstinspires.ftc.teamcode.subsystem;

import org.firstinspires.ftc.teamcode.Robot;

public class RobotSystem extends Subsystem{
    private State state;
    public static ShootingSystem shootingSystem;

    public void init(){
        shootingSystem = new ShootingSystem();
        shootingSystem.init();
        state = State.IDLE;
    }

    public void setIdle(){
        if (state!= State.IDLE){
            shootingSystem.setIdle();
            Robot.ramp.setIdleRamp();
            Robot.intake.setIdleIntake();
        }
    }
    public void setShooting(){
        if (state != State.SHOOTING){
            shootingSystem.setSpinUp();
            Robot.intake.setIdleIntake();
            Robot.ramp.setIdleRamp();
        }
        state = State.SHOOTING;
    }
    public void setShooting(double speed){
        if (state != State.SHOOTING){
            shootingSystem.setSpinUp(speed);
            Robot.intake.setIdleIntake();
            Robot.ramp.setIdleRamp();
        }
        state = State.SHOOTING;
    }

    public void setIntaking(){
        if (state != State.INTAKING){
            Robot.intake.setIntaking();
            Robot.ramp.setLoading();
            shootingSystem.setIdle();
        }
        state = State.INTAKING;
        doIntaking();
    }

    public void loop(){
        if (state == State.IDLE) doIdle();
        else if (state == State.INTAKING)doIntaking();
        else if (state == State.SHOOTING) doShooting();
    }

    private void doIdle(){}
    private void doIntaking(){
        if (Robot.ramp.getBallsLoaded() > 2){
            setShooting();
        }
    }
    public void doShooting(){
        if (shootingSystem.isIdle()){
            setIdle();
        }
    }

    public State getState(){
        return state;
    }
    public String getStateString(){
        return state.toString();
    }

    public boolean isIdle(){
        return state == State.IDLE;
    }

    public boolean isIntaking() {
        return state == State.INTAKING;
    }

    public boolean isShooting() {
        return state == State.SHOOTING;
    }

    public enum State {
        IDLE, INTAKING, SHOOTING
    }
}
