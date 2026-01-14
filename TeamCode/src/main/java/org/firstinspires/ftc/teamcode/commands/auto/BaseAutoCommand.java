package org.firstinspires.ftc.teamcode.commands.auto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.SequentialCommand;

public abstract class BaseAutoCommand extends OpMode {
    protected Command main;

    @Override
    public void init() {
        Robot.init(hardwareMap, telemetry, gamepad1, gamepad2, getInitialPose());
        Robot.setupParams(getGoalID());
        Robot.ramp.ballLoaded();Robot.ramp.ballLoaded();Robot.ramp.ballLoaded();
    }
    public abstract int getGoalID();

    public abstract Pose2D getInitialPose();

    @Override
    public void init_loop() {
        Robot.odometry.writeOutPosition(telemetry);
    }


    @Override
    public void start() {
        main = getMainCommand();
        main.begin();
    }
    public abstract Command getMainCommand();

    @Override
    public void loop() {
        Robot.update();
        telemetry.addData("Pattern ID", Robot.shooter.camera.getPatternId());
        if (!main.isFinished())
            main.loop();
        Robot.lastPosition = Robot.odometry.getOdoPosition();
        Robot.shooter.writeSpeeds(telemetry);
        Robot.odometry.writeOutPosition(telemetry);
        telemetry.addData("Drivetrain", Robot.drivetrain.getCurrentCommand());
    }
}
