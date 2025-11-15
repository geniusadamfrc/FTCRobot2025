package org.firstinspires.ftc.teamcode.commands.simple;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.simple.shooter.*;
import org.firstinspires.ftc.teamcode.commands.simple.ramp.*;

public class ShootBall extends Command {
    private Command speedUp;
    private Command moveRamp;
    private Command shooting;
    public ShootBall(){
        registerSubsystem(Robot.shooter);
        registerSubsystem(Robot.ramp);
    }
    @Override
    public void beginImpl() {
        speedUp = new SpeedUpForShooting(900.0);
        speedUp.begin();
    }

    @Override
    public void loopImpl() {
        if (!speedUp.isFinished()) speedUp.loop();
        if (speedUp.isFinished() && moveRamp == null){
            moveRamp = new PushBallUp();
            shooting = new HoldForShooting();
            moveRamp.begin();
            shooting.begin();
        }
        if( moveRamp != null){
            moveRamp.loop();
            shooting.loop();
        }
        if (shooting != null && shooting.isFinished()){
            moveRamp.finish();
            finish();
        }
    }
}
