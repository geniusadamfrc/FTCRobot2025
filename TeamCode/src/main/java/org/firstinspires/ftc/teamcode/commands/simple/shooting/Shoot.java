package org.firstinspires.ftc.teamcode.commands.simple.shooting;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystem.RobotSystem;

public class Shoot extends Command {

    @Override
    public void beginImpl() {
        RobotSystem.shootingSystem.setOkToFind(true);
        //RobotSystem.shootingSystem.setOkToShoot(true);
    }

    @Override
    public void loopImpl() {
        if(Robot.ramp.getBallsLoaded() == 0) finish();
    }
    @Override
    public void finishImpl(){
        RobotSystem.shootingSystem.setOkToFind(false);
        Robot.robot.setIdle();
    }
}
