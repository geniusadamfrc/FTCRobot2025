package org.firstinspires.ftc.teamcode.commands.simple;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.simple.drive.MoveToPointOnFieldSlow;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

public class DriveForwardAndIntake extends Command {
    private MoveToPointOnFieldSlow drive;
    private double distance;
    private MecanumDrive controller;
    private double heading;
    private boolean useCurrentHeading;
    public DriveForwardAndIntake(double distance, MecanumDrive controller) {
        this.distance = distance;
        this.controller = controller;
        useCurrentHeading = true;
    }
    public DriveForwardAndIntake(double distance, double desiredAngle, MecanumDrive controller){
        this.distance = distance;
        this.controller = controller;
        this.heading = desiredAngle;
        useCurrentHeading = false;
    }
    @Override
    public void beginImpl() {
        double currentY = Robot.odometry.getOdoPosition().getY(DistanceUnit.INCH);
        double currentX = Robot.odometry.getOdoPosition().getX(DistanceUnit.INCH);
        if (useCurrentHeading) {
            heading = Robot.odometry.getHeading();
        }
        drive = new MoveToPointOnFieldSlow(currentX+ distance, currentY, heading, controller);
        //rampUp = new SlowRampUp();
        drive.begin();
        Robot.robot.setIntaking();
    }

    @Override
    public void loopImpl() {
        drive.loop();
        if (drive.isFinished()){
            finish();
        }
    }
}
