package org.firstinspires.ftc.teamcode.commands.simple;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.commands.Command;

public class WaitMSeconds extends Command {
    private double mSeconds;
    private ElapsedTime timer;
    public WaitMSeconds(double mSeconds){
        this.mSeconds = mSeconds;
    }

    @Override
    public void beginImpl() {
        timer = new ElapsedTime();
    }

    @Override
    public void loopImpl() {
        if (timer.milliseconds() > mSeconds){
            finish();
        }
    }
}
