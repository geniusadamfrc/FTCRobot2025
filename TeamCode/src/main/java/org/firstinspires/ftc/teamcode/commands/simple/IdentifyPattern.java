package org.firstinspires.ftc.teamcode.commands.simple;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;

public class IdentifyPattern extends Command {
    ElapsedTime timer;
    public IdentifyPattern() {
        //registerSubsystem(Robot.shooter);
    }
    @Override
    public void beginImpl () {
        timer = new ElapsedTime();
    }

    @Override
    public void loopImpl () {
        Robot.shooter.camera.tryToObtainPatternID();
        if (Robot.shooter.camera.isPatternFound() || timer.milliseconds() >  2000){
            finish();
        }
    }
}