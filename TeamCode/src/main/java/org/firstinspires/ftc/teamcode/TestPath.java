package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.simple.DriveStraightPath;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@TeleOp(name = "TestPathing")
public class TestPath extends OpMode {
    private MecanumDrive drivePath;
    private Command c;
    @Override
    public void init() {
        Robot.init(hardwareMap, telemetry);
        drivePath = new MecanumDrive(hardwareMap, new Pose2d(0.0, 0.0, 0.0));
    }

    @Override
    public void loop() {
        if (gamepad1.a && c == null){
            c = new DriveStraightPath(Robot.drivetrain, drivePath, 20.0);
            c.begin();
        }
        if (c!= null) {
            c.loop();
            if (c.isFinished()) c = null;
        }
    }
}
