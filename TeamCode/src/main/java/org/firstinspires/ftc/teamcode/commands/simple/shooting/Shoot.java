package org.firstinspires.ftc.teamcode.commands.simple.shooting;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystem.RobotSystem;

public class Shoot extends Command {
    private ElapsedTime timer;
    public static double MAX_TIME = 5.0;
    @Override
    public void beginImpl() {
        Robot.robot.setOkToFind(true);
        timer = new ElapsedTime();
        //RobotSystem.shootingSystem.setOkToShoot(true);
    }

    @Override
    public void loopImpl() {
        if(Robot.ramp.getBallsLoaded() == 0) finish();
        if (timer.seconds() > MAX_TIME) finish();
    }
    @Override
    public void finishImpl(){
        Robot.robot.setIdle();
    }
}
