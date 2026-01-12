package org.firstinspires.ftc.teamcode.subsystem;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.CommandManager;
import org.firstinspires.ftc.teamcode.commands.simple.drive.AlignTargetOdo;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystem.shooter.Shooter;

public class ShootingSystem extends Subsystem{
    private Ramp ramp;
    private Shooter shooter;
    private Drivetrain drivetrain;

    private State state;
    private AlignTargetOdo alignTarget;

    private boolean okToFind;
    private boolean okToShoot;


    public ShootingSystem(){

    }
    public void init() {
        ramp = Robot.ramp;
        shooter = Robot.shooter;
        drivetrain = Robot.drivetrain;
        state = State.IDLE;
    }


    public void setOkToFind(boolean okToFind) {
        this.okToFind = okToFind;
    }

    public void setOkToShoot(boolean okToShoot) {
        this.okToShoot = okToShoot;
    }

    public void setIdle(){
        setOkToShoot(false);
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
    public void setFinding(){
        state = State.FINDING;
        if (alignTarget == null){
            alignTarget = new AlignTargetOdo(false);
            CommandManager.schedule(alignTarget);
        }
        doFinding();
    }

    private void idleDrivetrain(){
        if (alignTarget != null){
            if (!alignTarget.isFinished()) alignTarget.finish();
            alignTarget = null;
        }
        drivetrain.setIdle();
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
        
    }
    private void doFinding(){

    }
    private void doShooting(){}

    private enum State {
        IDLE, SPIN_UP, FINDING, SHOOTING
    }



}
