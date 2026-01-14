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
        shooter.setIdleShooter();
        idleDrivetrain();
        ramp.setIdleRamp();
        state=State.IDLE;
    }
    public void setSpinUp(){
        shooter.startShooting();
        ramp.setIdleRamp();
        idleDrivetrain();
        state = State.SPIN_UP;
    }
    public void setSpinUp(double defaultSpeed){
        shooter.startShooting(defaultSpeed);
        ramp.setIdleRamp();
        idleDrivetrain();
        state = State.SPIN_UP;
    }
    private void setShooting(){
        Robot.intake.setSlowIntaking();
        Robot.ramp.setFeeding();
        state = State.SHOOTING;
    }

    private void idleDrivetrain(){
        Robot.drivetrain.setDrive();
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
        idleDrivetrain();
    }
    private void doSpinUp(){
        if (okToFind){
            state = State.FINDING;
            Robot.drivetrain.setAlign();
        }
    }
    private void doFinding(){
        if (!okToFind){
            idleDrivetrain();
            state =  State.SPIN_UP;
        }
        else if (Robot.shooter.isReadyForShot() && aligner.isAligned()){
            setShooting();
        }
    }

    private void doShooting(){
        if (!Robot.shooter.isReadyForShot() || !aligner.isAligned() || !okToFind){
            state = State.SPIN_UP;
            Robot.ramp.setIdleRamp();
            Robot.intake.setIdleIntake();
            return;
        }
        if (ramp.isRampIdle()){
            if (ramp.getBallsLoaded() ==0) setIdle();
            else ramp.setFeeding();
        }
    }




    public void doTelemetry(Telemetry telemetry){
        telemetry.addData("Shooting System State", state.toString());
        telemetry.addData("Alignment", aligner.isAligned());
        telemetry.addData("Ready For Shot", shooter.isReadyForShot());
        telemetry.addData("OkToFind", okToFind);
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
