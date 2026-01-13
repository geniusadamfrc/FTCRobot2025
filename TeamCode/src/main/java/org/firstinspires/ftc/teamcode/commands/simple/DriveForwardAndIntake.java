package org.firstinspires.ftc.teamcode.commands.simple;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.simple.drive.MoveToPointOnFieldSlow;
import org.firstinspires.ftc.teamcode.commands.simple.ramp.TurnOnIntake;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

public class DriveForwardAndIntake extends Command {
    private MoveToPointOnFieldSlow drive;
    private double distance;
    private MecanumDrive controller;
    public DriveForwardAndIntake(double distance, MecanumDrive controller){
        this.distance = distance;
        this.controller = controller;
    }
    @Override
    public void beginImpl() {
        double currentY = Robot.odometry.getOdoPosition().getY(DistanceUnit.INCH);
        double currentX = Robot.odometry.getOdoPosition().getX(DistanceUnit.INCH);
        double currentHeading = Robot.odometry.getHeading();
        drive = new MoveToPointOnFieldSlow(currentX+ distance, currentY, currentHeading, controller);
        //rampUp = new SlowRampUp();
        drive.begin();
        Robot.robot.doIntaking();
    }

    @Override
    public void loopImpl() {
        drive.loop();
        if (drive.isFinished()){
            Robot.robot.setIdle();
            finish();
        }
    }
}
