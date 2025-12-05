package org.firstinspires.ftc.teamcode.commands.simple;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.simple.shooter.*;
import org.firstinspires.ftc.teamcode.commands.simple.ramp.*;
import org.firstinspires.ftc.teamcode.subsystem.shooter.TagNotFoundException;

public class ShootBall extends Command {
    private PushBallUp moveRamp;
    private Command shooting;
    public ShootBall(){
        registerSubsystem(Robot.shooter);
        registerSubsystem(Robot.ramp);
    }
    @Override
    public void beginImpl() {
        moveRamp = new PushBallUp();
        double targetSpeed;
        targetSpeed = Robot.shooter.getIdealShootingSpeed();
        if (targetSpeed < 600.0) targetSpeed = 620;
        shooting = new HoldForShooting(targetSpeed);
        moveRamp.begin();
        shooting.begin();

    }

    @Override
    public void loopImpl() {
        moveRamp.loop();
        shooting.loop();
        if ((shooting.isFinished() && moveRamp.hasMovedEnough())|| moveRamp.hasMovedMaximum()) {
            moveRamp.finish();
            finish();
        }
    }
}
