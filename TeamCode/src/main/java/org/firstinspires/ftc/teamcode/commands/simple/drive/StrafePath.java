package org.firstinspires.ftc.teamcode.commands.simple.drive;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain;

public class StrafePath extends DriveCommand {


    private double inches;
    private Action action;
    private MecanumDrive controller;
    public StrafePath(Drivetrain drivetrain, MecanumDrive drivePath, double inches){

        this.inches = inches;
        this.registerCommandSubsystem(drivetrain);
        this.controller = drivePath;
    }

    @Override
    public void beginImpl() {
        controller.setDrivetrainController(drivetrainController);
        action = controller.actionBuilder(new Pose2d(
                Robot.odometry.getOdoPosition().getX(DistanceUnit.INCH),
                        Robot.odometry.getOdoPosition().getY(DistanceUnit.INCH),
                        Robot.odometry.getOdoPosition().getHeading(AngleUnit.RADIANS)))
                .lineToX(this.inches)
                //.strafeTo(new Vector2d(44.5, 30))
                //.turn(Math.toRadians(180))
                //.lineToX(47.5)
                .waitSeconds(0.5).build();
    }

    @Override
    public void loopImpl() {
        if (!action.run(new TelemetryPacket())) finish();
    }
}
