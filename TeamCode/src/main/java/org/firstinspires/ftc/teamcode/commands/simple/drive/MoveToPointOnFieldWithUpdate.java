package org.firstinspires.ftc.teamcode.commands.simple.drive;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

public class MoveToPointOnFieldWithUpdate extends DriveCommand {


    private double x;
    private double y;
    private double heading;
    private Action action;
    private MecanumDrive controller;
    public MoveToPointOnFieldWithUpdate(double x, double y, double heading, MecanumDrive controller) {

        this.x = x;
        this.y = y;
        this.heading = heading;
        this.registerCommandSubsystem(Robot.drivetrain);
        this.controller = controller;
    }
    public void update(){
        int pattern = Robot.shooter.camera.getPatternId();
        if (pattern ==21) return;
        else if (pattern ==22){
            y=0;
        }
        else if (pattern == 23){
            y=-24;
        }
    }

    @Override
    public void beginImpl() {
        controller.setDrivetrainController(drivetrainController);
        update();
        action = controller.actionBuilder(new Pose2d(
                        Robot.odometry.getOdoPosition().getX(DistanceUnit.INCH),
                        Robot.odometry.getOdoPosition().getY(DistanceUnit.INCH),
                        Robot.odometry.getOdoPosition().getHeading(AngleUnit.RADIANS)))
                .setTangent(Robot.odometry.odo.getHeading(AngleUnit.RADIANS))
                .splineToLinearHeading(new Pose2d(x, y, Math.toRadians(heading)), Math.toRadians(heading))
                //.strafeTo(new Vector2d(44.5, 30))
                //.turn(Math.toRadians(180))
                //.lineToX(4
                .build();
    }

    @Override
    public void loopImpl() {
        if (!action.run(new TelemetryPacket())) finish();
    }
}