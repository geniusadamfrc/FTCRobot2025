package org.firstinspires.ftc.teamcode.commands.simple.drive;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;

public class Turn90DegreesPath extends Command {
    private Action action;
    private int angleInDegrees;
    public Turn90DegreesPath(int angleInDegrees){
        //this.imu = imu;
        registerSubsystem(Robot.drivetrain);
        this.angleInDegrees = angleInDegrees;
    }
    @Override
    public void beginImpl(){
        action = Robot.drivetrain.roadRunnerController.actionBuilder(new Pose2d(
                        Robot.drivetrain.getOdoPosition().getX(DistanceUnit.INCH),
                        Robot.drivetrain.getOdoPosition().getY(DistanceUnit.INCH),
                        Robot.drivetrain.getOdoPosition().getHeading(AngleUnit.RADIANS)))
                .turn(Math.toRadians(angleInDegrees))
                //.lineToX(47.5)
                .waitSeconds(0.5).build();
    }
    @Override
    public void loopImpl() {
        if (!action.run(new TelemetryPacket())) finish();
    }
}