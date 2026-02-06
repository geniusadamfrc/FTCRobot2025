package org.firstinspires.ftc.teamcode.subsystem.drivetrain;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

public class DrivetrainAlignerPID  extends DrivetrainAligner {

    private double Kp = 0.01;
    private double Ki = 0.03;
    private double Kd = 0.00;
    private double integralSum = 0;
    ElapsedTime timer = new ElapsedTime();
    private double staticFeedForward = 0.05;
    double out=0;


    @Override
    public void startAlign(){
        running = true;
        updateTargetOdo();
        timer.reset();
        integralSum = 0;
        lastError = 0;
    }
    @Override
    public void loop() {
        double error = Robot.odometry.getHeading() - targetOdo;
        // rate of change of the error
        double derivative = (error - lastError) / timer.seconds();
        // sum of all error over time
        if (error*lastError < 0) integralSum = 0;
        integralSum = integralSum + (error * timer.seconds());

        out = (Kp * error) + (Ki * integralSum) + (Kd * derivative);
        double feedForward = staticFeedForward;
        if (isAligned() ) feedForward =0;
        out = out + (out < 0 ? -feedForward : feedForward);
        timer.reset();
        lastError = error;
        if (!running) return;

        Robot.drivetrain.driveRobotRelative(0.0, out, 0.0);
        // reset the timer for next time
    }


}
