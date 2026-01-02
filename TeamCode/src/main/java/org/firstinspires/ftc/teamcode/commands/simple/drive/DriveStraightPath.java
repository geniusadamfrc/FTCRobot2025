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

public class DriveStraightPath extends Command {


    private double inches;
    private Action action;
    private MecanumDrive roadrunner;
    public DriveStraightPath(Drivetrain drivetrain, MecanumDrive drivePath, double inches){
        this.roadrunner = drivePath;
        this.inches = inches;
        this.registerSubsystem(drivetrain);
    }

    @Override
    public void beginImpl() {
        action = roadrunner.actionBuilder(new Pose2d(
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
