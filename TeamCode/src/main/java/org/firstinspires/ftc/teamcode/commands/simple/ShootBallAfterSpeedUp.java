package org.firstinspires.ftc.teamcode.commands.simple;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.simple.ramp.PushBallUp;
import org.firstinspires.ftc.teamcode.commands.simple.shooter.HoldForShooting;
import org.firstinspires.ftc.teamcode.commands.simple.shooter.SpeedUpForShooting;
import org.firstinspires.ftc.teamcode.subsystem.shooter.TagNotFoundException;

public class ShootBallAfterSpeedUp extends Command {
    private Command speedUp;
    private PushBallUp moveRamp;
    private Command shooting;
    public ShootBallAfterSpeedUp(){
        registerSubsystem(Robot.shooter);
        registerSubsystem(Robot.ramp);
    }
    @Override
    public void beginImpl() {
        double targetSpeed;
        targetSpeed = Robot.shooter.getIdealShootingSpeed();
        if (targetSpeed < 600.0) targetSpeed = 620;
        speedUp = new SpeedUpForShooting(targetSpeed);
        speedUp.begin();
    }

    @Override
    public void loopImpl() {
        if (!speedUp.isFinished()) speedUp.loop();
        if (speedUp.isFinished() && moveRamp == null){
            moveRamp = new PushBallUp();
            shooting = new HoldForShooting(620);
            moveRamp.begin();
            shooting.begin();
        }
        if( moveRamp != null){
            moveRamp.loop();
            shooting.loop();
        }
        if (shooting != null && ((shooting.isFinished() && moveRamp.hasMovedEnough())|| moveRamp.hasMovedMaximum())) {
            moveRamp.finish();
            finish();
        }
    }
}
