package org.firstinspires.ftc.teamcode.commands.simple.shooter;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;


public class HoldForShooting extends Command{
    public HoldForShooting(){
        registerSubsystem(Robot.shooter);
    }
    @Override
    public void beginImpl() {
    }

    @Override
    public void loopImpl() {
        if(Robot.shooter.isBallShot()){
            Robot.shooter.clearBallShot();
            finish();
        }
    }
}
