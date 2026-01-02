package org.firstinspires.ftc.teamcode.commands.simple.drive;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;

public class MoveToPointOnFieldWithUpdate extends Command {


    private double x;
    private double y;
    private double heading;
    private Action action;

    public MoveToPointOnFieldWithUpdate(double x, double y, double heading) {

        this.x = x;
        this.y = y;
        this.heading = heading;
        this.registerSubsystem(Robot.drivetrain);
    }
    public void update(){
        int pattern = Robot.shooter.camera.getPatternId();
        if (pattern ==21) return;
        else if (pattern ==22){
            y=0;
        }
        else if (pattern == 23){
            y=-24;
        }
    }

    @Override
    public void beginImpl() {
        update();
        action = Robot.drivetrain.roadRunnerController.actionBuilder(new Pose2d(
                        Robot.odometry.getOdoPosition().getX(DistanceUnit.INCH),
                        Robot.odometry.getOdoPosition().getY(DistanceUnit.INCH),
                        Robot.odometry.getOdoPosition().getHeading(AngleUnit.RADIANS)))
                .setTangent(Robot.drivetrain.odo.getHeading(AngleUnit.RADIANS))
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