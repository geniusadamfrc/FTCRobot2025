package org.firstinspires.ftc.teamcode.commands.simple.drive;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

public class Turn90DegreesPath extends DriveCommand {
    private Action action;
    private int angleInDegrees;
    private MecanumDrive controller;
    public Turn90DegreesPath(int angleInDegrees, MecanumDrive controller){
        //this.imu = imu;
        registerCommandSubsystem(Robot.drivetrain);
        this.angleInDegrees = angleInDegrees;
        this.controller = controller;
    }
    @Override
    public void beginImpl(){
        controller.setDrivetrainController(drivetrainController);
        action = controller.actionBuilder(new Pose2d(
                        Robot.odometry.getOdoPosition().getX(DistanceUnit.INCH),
                        Robot.odometry.getOdoPosition().getY(DistanceUnit.INCH),
                        Robot.odometry.getOdoPosition().getHeading(AngleUnit.RADIANS)))
                .turnTo(Math.toRadians(angleInDegrees))
                //.lineToX(47.5)
                .build();
    }
    @Override
    public void loopImpl() {
        if (!action.run(new TelemetryPacket())) finish();
    }
    @Override
    public String writeName() {
        return "Turn 90 Degrees Path";
    }
}