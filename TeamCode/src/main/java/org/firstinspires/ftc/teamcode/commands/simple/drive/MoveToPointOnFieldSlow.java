package org.firstinspires.ftc.teamcode.commands.simple.drive;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Arclength;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.PosePath;
import com.acmerobotics.roadrunner.VelConstraint;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

public class MoveToPointOnFieldSlow extends DriveCommand {


    private double x;
    private double y;
    private double heading;
    private Action action;
    private MecanumDrive roadrunner;

    public MoveToPointOnFieldSlow(double x, double y, double heading, MecanumDrive controller) {

        this.x = x;
        this.y = y;
        this.heading = heading;
        this.registerCommandSubsystem(Robot.drivetrain);
        this.roadrunner = controller;
    }

    @Override
    public void beginImpl() {
        //Robot.drivetrain.roadRunnerController.defaultVelConstraint
        action = roadrunner.actionBuilder(new Pose2d(
                        Robot.odometry.getOdoPosition().getX(DistanceUnit.INCH),
                        Robot.odometry.getOdoPosition().getY(DistanceUnit.INCH),
                        Robot.odometry.getOdoPosition().getHeading(AngleUnit.RADIANS)))
                .setTangent(Robot.odometry.odo.getHeading(AngleUnit.RADIANS))
                .splineToLinearHeading(new Pose2d(x, y, Math.toRadians(heading)), Math.toRadians(heading), new VelConstraint() {
                    @Override
                    public double maxRobotVel(@NonNull Pose2dDual<Arclength> pose2dDual, @NonNull PosePath posePath, double v) {
                        return 9.0;
                    }
                })
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