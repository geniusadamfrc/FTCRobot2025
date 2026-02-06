package org.firstinspires.ftc.teamcode.commands.simple;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;

public class Intake extends Command {
    @Override
    public void beginImpl() {
        Robot.robot.setIntaking();
    }

    @Override
    public void loopImpl() {
        finish();
    }
}
