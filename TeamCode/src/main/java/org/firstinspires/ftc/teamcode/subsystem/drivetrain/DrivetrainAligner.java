package org.firstinspires.ftc.teamcode.subsystem.drivetrain;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystem.shooter.TagNotFoundException;

public abstract class DrivetrainAligner {
    protected double targetOdo;
    protected double lastError = 0;
    protected double defaultAngle;

    public DrivetrainAligner(){


    }

    public abstract void startAlign();
    public abstract void loop();
    public void updateTargetOdo(){
        try {
            this.targetOdo = Robot.odometry.getHeading() + Robot.shooter.camera.getBearing();
        } catch (TagNotFoundException e) {
        }
    }
    public boolean running;

    public boolean isAligned(){
        double error = Robot.odometry.getHeading() - targetOdo;
        return Math.abs(error) < 1.5;
    }
    public double getTargetOdo(){
        return targetOdo;
    }
    public double getLastError(){
        return lastError;
    }
    public void setDefaultAngle(double defaultAngle) {
        this.targetOdo = defaultAngle;
    }


}