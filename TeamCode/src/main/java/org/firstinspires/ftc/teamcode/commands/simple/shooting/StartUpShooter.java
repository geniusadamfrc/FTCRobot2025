package org.firstinspires.ftc.teamcode.commands.simple.shooting;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.*;
public class StartUpShooter extends Command{

    private double speed;

    public StartUpShooter(double speed){
        this.speed = speed;
    }

    @Override
    public void beginImpl() {
        Robot.robot.setShooting(speed);
    }

    @Override
    public void loopImpl() {
        finish();
    }
}
