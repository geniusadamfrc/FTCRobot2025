package org.firstinspires.ftc.teamcode.commands.simple.shooter;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;


public class HoldForShooting extends Command{
    public static final double SPEED_DROP = 150.0;
    private double speed;
    public HoldForShooting(double speed){
        registerSubsystem(Robot.shooter);
        this.speed = speed;
    }
    @Override
    public void beginImpl() {
        Robot.shooter.setTargetSpeed(speed);
    }

    @Override
    public void loopImpl() {
        if(!Robot.shooter.isAtSpeed(SPEED_DROP)) finish();
    }
}
