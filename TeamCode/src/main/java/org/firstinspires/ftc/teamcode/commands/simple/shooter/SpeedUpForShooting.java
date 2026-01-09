package org.firstinspires.ftc.teamcode.commands.simple.shooter;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;

public class SpeedUpForShooting extends Command {
    private double targetSpeed;
    public final static double TOLERANCE = 50.0;
    public SpeedUpForShooting(double targetSpeed){
        registerBasicSubsystem(Robot.shooter);
        this.targetSpeed = targetSpeed;
    }
    @Override
    public void beginImpl() {
        Robot.shooter.setSpeedTolerance(TOLERANCE);
        Robot.shooter.startShooting(this.targetSpeed);
    }

    @Override
    public void loopImpl() {
        if (Robot.shooter.isReadyForShot()) finish();
    }
}
