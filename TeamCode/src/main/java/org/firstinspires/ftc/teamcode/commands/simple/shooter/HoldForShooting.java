package org.firstinspires.ftc.teamcode.commands.simple.shooter;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;


public class HoldForShooting extends Command{
    public static final double TARGET_SPEEED = 900.0;
    public static final double SPEED_DROP = 150.0;
    public HoldForShooting(){
        registerSubsystem(Robot.shooter);
    }
    @Override
    public void beginImpl() {
        Robot.shooter.setTargetSpeed(TARGET_SPEEED);
    }

    @Override
    public void loopImpl() {
        if(Robot.shooter.isAtSpeed(SPEED_DROP)) finish();
    }
}
