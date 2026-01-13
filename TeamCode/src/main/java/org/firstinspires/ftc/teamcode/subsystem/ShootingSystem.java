package org.firstinspires.ftc.teamcode.subsystem;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystem.shooter.Shooter;

public class ShootingSystem extends Subsystem{
    public static Ramp ramp;
    public static Shooter shooter;
    public static Drivetrain.DrivetrainAligner aligner;

    private State state;
    private boolean okToFind;


    public ShootingSystem(){

    }
    public void init() {
        ramp = Robot.ramp;
        shooter = Robot.shooter;
        aligner = Robot.drivetrain.aligner;
        state = State.IDLE;
    }


    public void setOkToFind(boolean okToFind) {
        this.okToFind = okToFind;
    }


    public void setIdle(){
        setOkToFind(false);
        if (state != State.IDLE) {
            shooter.setIdleShooter();
            idleDrivetrain();
            ramp.setIdleRamp();
        }
    }
    public void setSpinUp(){
        shooter.startShooting();
        ramp.setIdleRamp();
        idleDrivetrain();
        doSpinUp();
    }
    public void setSpinUp(double defaultSpeed){
        shooter.startShooting(defaultSpeed);
        ramp.setIdleRamp();
        idleDrivetrain();
        doSpinUp();
    }
    private void setShooting(){
        if (state != State.SHOOTING){
            Robot.intake.setSlowIntaking();
            Robot.ramp.setFeeding();
        }
        state = State.SHOOTING;
    }

    private void idleDrivetrain(){
        aligner.setOff();
    }

    @Override
    public void loop(){
        if (state == State.IDLE) doIdle();
        else if (state == State.SPIN_UP) doSpinUp();
        else if (state == State.FINDING) doFinding();
        else if (state == State.SHOOTING) doShooting();
    }
    private void doIdle(){
        if (okToFind){
            setSpinUp();
        }
    }
    private void doSpinUp(){
        if (okToFind){
            state = State.FINDING;
            doFinding();
        }
    }
    private void doFinding(){
        doAligning();
        if (!okToFind){
            idleDrivetrain();
            state =  State.SPIN_UP;
        }
        else if (Robot.shooter.isReadyForShot() && aligner.isAligned()){
            setShooting();
        }
    }

    private void doShooting(){
        if (!Robot.shooter.isReadyForShot() || !aligner.isAligned()){
            state = State.FINDING;
            Robot.ramp.setIdleRamp();
            Robot.intake.setIdleIntake();
            return;
        }
        if (ramp.isRampIdle()){
            if (ramp.getBallsLoaded() ==0) setIdle();
            else ramp.setFeeding();
        }
    }

    private void doAligning(){
        aligner.setAlign();
    }



    public void doTelemetry(Telemetry telemetry){
        telemetry.addData("Shooting System State", state.toString());
        telemetry.addData("Alignment", aligner.isAligned());
        telemetry.addData("Ready For Shot", shooter.isReadyForShot());
    }


    public boolean isReadyForShot() {
        return state == State.FINDING && shooter.isReadyForShot() && aligner.isAligned();
    }
    public boolean isIdle(){
        return state == State.IDLE;
    }

    public boolean isShooting(){
        return state == State.SHOOTING;
    }



    private enum State {
        IDLE, SPIN_UP, FINDING, SHOOTING
    }



}
