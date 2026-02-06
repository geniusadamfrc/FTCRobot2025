package org.firstinspires.ftc.teamcode.commands.simple.drive;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain;

public class DriveStraightPedro extends DriveCommand {


    private double inches;
    private Follower follower;
    private Path scorePreload;
    private Telemetry telemetry;
    public DriveStraightPedro(Drivetrain drivetrain, Follower follower,  double inches, Telemetry telemetry){

        this.inches = inches;
        this.follower = follower;
        this.telemetry = telemetry;

    }

    @Override
    public void beginImpl() {
        Pose startPose = new Pose(Robot.odometry.odo.getPosX(DistanceUnit.INCH), Robot.odometry.odo.getPosY(DistanceUnit.INCH), Robot.odometry.odo.getHeading(AngleUnit.RADIANS));
        Pose scorePose = new Pose(startPose.getX() + 24, startPose.getY()-24, startPose.getHeading()); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
        scorePreload = new Path(new BezierLine(startPose, scorePose));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());
        follower.followPath(scorePreload);

    }

    @Override
    public void loopImpl()
    {
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        follower.update();
        if (!follower.isBusy()) finish();
    }

}
