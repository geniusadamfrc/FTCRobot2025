package org.firstinspires.ftc.teamcode.commands.simple;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.simple.ramp.PushBallUp;

public class ShootBallAfterSpeedUp extends Command {
    private PushBallUp moveRamp;
    public ShootBallAfterSpeedUp(){
        registerBasicSubsystem(Robot.shooter);
        registerBasicSubsystem(Robot.ramp);
    }
    @Override
    public void beginImpl() {
        Robot.shooter.startShooting();
    }

    @Override
    public void loopImpl() {
        if (Robot.shooter.isReadyForShot() && moveRamp == null){
            moveRamp = new PushBallUp();
            moveRamp.begin();
        }
        if( moveRamp != null){
            moveRamp.loop();
            if ((Robot.shooter.isBallShot() && moveRamp.hasMovedEnough())|| moveRamp.hasMovedMaximum()) {
                moveRamp.finish();
                Robot.shooter.clearBallShot();
                finish();
            }
        }
    }
}
