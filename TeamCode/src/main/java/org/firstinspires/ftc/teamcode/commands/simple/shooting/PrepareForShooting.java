package org.firstinspires.ftc.teamcode.commands.simple.shooting;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;

public class PrepareForShooting  extends Command {
    private double targetSpeed;
    public final static double TOLERANCE = 20.0;
    public final static int ITERATIONS_TO_ASSUME_FULL_SPEED = 10;
    private int iterations;
    private boolean canFinish;
    public PrepareForShooting(double targetSpeed){
        registerSubsystem(Robot.shooter);
        this.targetSpeed = targetSpeed;
    }
    @Override
    public void beginImpl() {
        Robot.shooter.setTargetSpeed(this.targetSpeed);
        Robot.shooter.setSpeedTolerance(TOLERANCE);
        iterations = 0;
        canFinish = false;
    }

    @Override
    public void loopImpl() {
        if (Robot.shooter.isAtSpeed()){
            if (iterations >= ITERATIONS_TO_ASSUME_FULL_SPEED)
                canFinish = true;
            else{
                iterations++;
                canFinish = false;
            }
        }else {
            canFinish = false;
            iterations = 0;
        }
    }
    public boolean canFinish(){
        return canFinish;
    }
}
