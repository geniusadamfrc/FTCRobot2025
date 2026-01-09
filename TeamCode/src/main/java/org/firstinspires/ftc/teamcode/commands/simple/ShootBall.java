package org.firstinspires.ftc.teamcode.commands.simple;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.simple.shooter.*;
import org.firstinspires.ftc.teamcode.commands.simple.ramp.*;
import org.firstinspires.ftc.teamcode.subsystem.shooter.TagNotFoundException;

public class ShootBall extends Command {
    private PushBallUp moveRamp;
    public ShootBall(){
        registerBasicSubsystem(Robot.shooter);
        registerBasicSubsystem(Robot.ramp);
    }
    @Override
    public void beginImpl() {
        moveRamp = new PushBallUp();
        moveRamp.begin();
        Robot.shooter.startShooting();
    }

    @Override
    public void loopImpl() {
        moveRamp.loop();
        if ((Robot.shooter.isBallShot() && moveRamp.hasMovedEnough())|| moveRamp.hasMovedMaximum()) {
            Robot.shooter.clearBallShot();
            moveRamp.finish();
            finish();
        }
    }
}
