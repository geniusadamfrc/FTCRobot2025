package org.firstinspires.ftc.teamcode.commands.simple;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain;

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
        action = drivePath.actionBuilder(new Pose2d(
                Robot.drivetrain.getOdoPosition().getX(DistanceUnit.MM),
                        Robot.drivetrain.getOdoPosition().getY(DistanceUnit.MM),
                        Robot.drivetrain.getOdoPosition().getHeading(AngleUnit.DEGREES)))
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
