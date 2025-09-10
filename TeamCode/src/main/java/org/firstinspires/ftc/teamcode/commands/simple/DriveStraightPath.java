package org.firstinspires.ftc.teamcode.commands.simple;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystem.Drivetrain;

public class DriveStraightPath extends Command {

    private Drivetrain drivetrain;
    private MecanumDrive drivePath;
    private double inches;
    private Action action;
    public DriveStraightPath(Drivetrain drivetrain, MecanumDrive drivePath, double inches){
        this.drivePath = drivePath;
        this.drivetrain = drivetrain;
        this.inches = inches;
        this.registerSubsystem(drivetrain);
    }

    @Override
    public void beginImpl() {
        action = drivePath.actionBuilder(new Pose2d(0.0, 0.0, 0.0))
                //.lineToYSplineHeading(33, Math.toRadians(0))
                //.waitSeconds(2)
                //.setTangent(Math.toRadians(90))
                //.lineToY(48)
                //.setTangent(Math.toRadians(0))
                .lineToX(32)
                //.strafeTo(new Vector2d(44.5, 30))
                //.turn(Math.toRadians(180))
                //.lineToX(47.5)
                .waitSeconds(3).build();
    }

    @Override
    public void loopImpl() {
        if (!action.run(new TelemetryPacket())) finish();
    }
}
