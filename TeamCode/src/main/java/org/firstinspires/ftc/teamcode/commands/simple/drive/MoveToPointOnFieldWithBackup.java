package org.firstinspires.ftc.teamcode.commands.simple.drive;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;

public class MoveToPointOnFieldWithBackup extends Command {


    private double x;
    private double y;
    private double heading;
    private Action action;

    public MoveToPointOnFieldWithBackup(double x, double y, double heading) {

        this.x = x;
        this.y = y;
        this.heading = heading;
        this.registerSubsystem(Robot.drivetrain);
    }

    @Override
    public void beginImpl() {
        double initialX = Robot.drivetrain.getOdoPosition().getX(DistanceUnit.INCH);
        double initialY = Robot.drivetrain.getOdoPosition().getY(DistanceUnit.INCH);
        double initialHeading =Robot.drivetrain.getOdoPosition().getHeading(AngleUnit.RADIANS);

                action = Robot.drivetrain.roadRunnerController.actionBuilder(new Pose2d(
                        initialX,
                        initialY,
                        initialHeading))
                .setTangent(Robot.drivetrain.odo.getHeading(AngleUnit.RADIANS))
                .splineToLinearHeading(
                        new Pose2d(initialX +30, initialY, initialHeading), initialHeading
                )
                .turnTo(90)
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