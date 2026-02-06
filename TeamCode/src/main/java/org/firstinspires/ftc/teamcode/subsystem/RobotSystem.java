package org.firstinspires.ftc.teamcode.subsystem;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.DrivetrainAligner;
import org.firstinspires.ftc.teamcode.subsystem.shooter.Shooter;

public class RobotSystem extends Subsystem{
    private static Ramp ramp;
    private static Shooter shooter;
    private static DrivetrainAligner aligner;
    private boolean okToFind;
    private State state;

    public void init(){
        ramp = Robot.ramp;
        shooter = Robot.shooter;
        aligner=Robot.drivetrain.aligner;
        okToFind =false;
        state = State.IDLE;
    }

    public void setOkToFind(boolean okToFind) {
        this.okToFind = okToFind;

    }
    public boolean isIdle(){
        return state == State.IDLE;
    }

    public boolean isIntaking() {
        return state == State.INTAKING;
    }

    public boolean isShooting(){
        return state == State.SHOOTING;
    }




    public void setIdle(){
        if (state == State.IDLE) return;
        if (state == State.FINDING || state == State.SHOOTING){
            Robot.drivetrain.setDrive();
        }
        setOkToFind(false);
        shooter.setIdleShooter();
        ramp.setIdleRamp();
        Robot.intake.setIdleIntake();
        state = State.IDLE;
    }
    public void setStartShooting(){
        if (state == State.IDLE || state == State.INTAKING || state == State.INTAKING_LAST_LOAD) {
            Robot.intake.setIdleIntake();
            shooter.startShooting();
            ramp.setIdleRamp();
            state = State.SPIN_UP;
        }
    }
    public void setStartShooting(double speed){
        if (state == State.IDLE || state == State.INTAKING) {
            Robot.intake.setIdleIntake();
            shooter.startShooting(speed);
            ramp.setIdleRamp();
            state = State.SPIN_UP;
        }
    }



    public void setIntaking(){
        if (state == State.IDLE){
            setOkToFind(false);
            shooter.setIdleShooter();
            Robot.intake.setIntaking();
            ramp.setLoading();
            state = State.INTAKING;
        }
    }
    @Override
    public void loop(){
        if (state == State.IDLE) {}
        if (state == State.INTAKING)doIntaking();
        if (state == State.INTAKING_LAST_LOAD) doIntakingLastLoad();
        if (state == State.SPIN_UP) doSpinUp();
        if (state == State.FINDING) doFinding();
        if (state == State.SHOOTING) doShooting();
    }
    private void doSpinUp(){

        if (okToFind){
            state = State.FINDING;
            Robot.drivetrain.setAlign();
        }
    }
    private void doFinding(){
        if (!okToFind){
            Robot.drivetrain.setDrive();
            state =  State.SPIN_UP;
        }
        else if (Robot.shooter.isReadyForShot() && aligner.isAligned()){
            Robot.intake.setSlowIntaking();
            Robot.ramp.setFeeding();
            state = State.SHOOTING;
        }
    }
    private void doShooting(){
        if (ramp.isRampIdle()){
            if (ramp.getBallsLoaded() ==0){
                Robot.drivetrain.setDrive();
                setIdle();
            }
            else{
                state = State.SPIN_UP;
                Robot.drivetrain.setDrive();
                Robot.intake.setIdleIntake();
            }
        }
        else if (!Robot.shooter.isReadyForShot() || !aligner.isAligned() || !okToFind){
            state = State.SPIN_UP;
            Robot.ramp.setIdleRamp();
            Robot.intake.setIdleIntake();
            Robot.drivetrain.setDrive();
        }

    }
    private void doIntaking(){
        if (Robot.ramp.getBallsLoaded() > 2){
            state = State.INTAKING_LAST_LOAD;
            ramp.setLastLoad();
        }
    }
    private void doIntakingLastLoad(){
        if (Robot.ramp.isRampIdle()){
            setStartShooting();
        }
    }
    public boolean isReadyForShot() {
        return state == State.FINDING && shooter.isReadyForShot() && aligner.isAligned();
    }

    public boolean isInShootingMode() {
        return state == State.SPIN_UP || state == State.FINDING || state == State.SHOOTING;
    }

    private enum State {
        IDLE, INTAKING, INTAKING_LAST_LOAD, SPIN_UP, FINDING, SHOOTING
    }

    public void doTelemetry(Telemetry telemetry){
        telemetry.addData("Robot State", state.toString());
        telemetry.addData("Shooting System State", state.toString());
        telemetry.addData("Alignment", aligner.isAligned());
        telemetry.addData("Ready For Shot", shooter.isReadyForShot());
        telemetry.addData("OkToFind", okToFind);
    }

}
