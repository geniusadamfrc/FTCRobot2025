package org.firstinspires.ftc.teamcode.commands.simple.drive;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;

public class AlignTarget extends Command {

    private double Kp = 0.01;
    private double Ki = 0.0;
    private double Kd = 0.0;
    private double integralSum = 0;

    private double lastError = 0;
    ElapsedTime timer;
    private double staticFeedForward = 0.1;
    public AlignTarget(){
        registerSubsystem(Robot.drivetrain);
        registerSubsystem(Robot.shooter);
    }
    @Override
    public void beginImpl() {
        timer = new ElapsedTime();
    }

    @Override
    public void loopImpl() {
        double error = -Robot.shooter.getBearing();
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
    }
}
