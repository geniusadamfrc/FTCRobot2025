package org.firstinspires.ftc.teamcode.commands.simple.drive;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;

public class MoveToPointOnFieldPedro extends Command {


    private double x;
    private double y;
    private double heading;
    private Path  path;
    private Follower follower;

    public MoveToPointOnFieldPedro(double x, double y, double heading) {

        this.x = x;
        this.y = y;
        this.heading = heading;
        this.registerSubsystem(Robot.drivetrain);
        this.follower = Robot.drivetrain.follower;
    }

    @Override
    public void beginImpl() {
        Pose startPose = new Pose(Robot.drivetrain.odo.getPosX(DistanceUnit.INCH), Robot.drivetrain.odo.getPosY(DistanceUnit.INCH), Robot.drivetrain.odo.getHeading(AngleUnit.RADIANS));
        Pose endPose = new Pose(x, y, heading); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
        path = new Path(new BezierLine(startPose, endPose));
        path.setLinearHeadingInterpolation(startPose.getHeading(), endPose.getHeading());
        follower.followPath(path);
        //follower.setStartingPose(startPose);
    }

    @Override
    public void loopImpl()
    {
        follower.update();
        if (!follower.isBusy()) finish();
    }
}