package org.firstinspires.ftc.teamcode.commands.simple.drive;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;

public class AlignTarget extends Command {

    private double Kp = 0.05;
    private double Ki = 0.01;
    private double Kd = 0.0;
    private double integralSum = 0;

    private double lastError = 0;
    private ElapsedTime timer;
    private double staticFeedForward = 0.05;
    private boolean allowFinish;
    public AlignTarget(boolean allowFinish){
        registerCommandSubsystem(Robot.drivetrain);
        registerBasicSubsystem(Robot.shooter);
        this.allowFinish = allowFinish;
    }
    @Override
    public void beginImpl() {
        timer = new ElapsedTime();
    }

    @Override
    public void loopImpl() {
        double error = -Robot.shooter.camera.getBearing();
        // rate of change of the error
        double derivative = (error - lastError) / timer.seconds();
        // sum of all error over time
        integralSum = integralSum + (error * timer.seconds());

        double out = (Kp * error) + (Ki * integralSum) + (Kd * derivative);
        out = out + (out < 0 ? -staticFeedForward : staticFeedForward);
        Robot.drivetrain.driveRobotRelative(0.0, out, 0.0);
        lastError = error;

        // reset the timer for next time
        timer.reset();
        if (Math.abs(error) < 3.0 && allowFinish) finish();
    }
}
