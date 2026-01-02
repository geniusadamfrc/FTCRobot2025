package org.firstinspires.ftc.teamcode.commands.simple;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.simple.drive.MoveToPointOnFieldSlow;
import org.firstinspires.ftc.teamcode.commands.simple.ramp.TurnOnIntake;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

public class DriveForwardAndIntake extends Command {
    private TurnOnIntake intake;
    private MoveToPointOnFieldSlow drive;
    //private SlowRampUp rampUp;
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
        intake = new TurnOnIntake(-0.25);
        //rampUp = new SlowRampUp();
        drive.begin();
        intake.begin();

    }

    @Override
    public void loopImpl() {
        intake.loop();
        drive.loop();
      //  rampUp.loop();
        if (drive.isFinished()){
            intake.finish();
        //    rampUp.finish();
            finish();
        }
    }
}
