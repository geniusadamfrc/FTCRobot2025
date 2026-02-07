package org.firstinspires.ftc.teamcode.commands.simple.drive;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

public class MoveToPointOnFieldWithBackup extends DriveCommand {


    private double x;
    private double y;
    private double heading;
    private double startingHeading;
    private Action action;
    private MecanumDrive controller;
    public MoveToPointOnFieldWithBackup(double x, double y, double heading, double startingHeading, MecanumDrive controller) {
        this.controller = controller;
        this.x = x;
        this.y = y;
        this.heading = heading;
        this.startingHeading = startingHeading;
        this.registerCommandSubsystem(Robot.drivetrain);
    }

    @Override
    public void beginImpl() {
        controller.setDrivetrainController(drivetrainController);
        double initialX = Robot.odometry.getOdoPosition().getX(DistanceUnit.INCH);
        double initialY = Robot.odometry.getOdoPosition().getY(DistanceUnit.INCH);
        double initialHeading =Robot.odometry.getOdoPosition().getHeading(AngleUnit.RADIANS);

                action = controller.actionBuilder(new Pose2d(
                        initialX,
                        initialY,
                        initialHeading))
                .setTangent(Math.toRadians(startingHeading))
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
    @Override
    public String writeName() {
        return "Move To Point On Field Backup";
    }
}