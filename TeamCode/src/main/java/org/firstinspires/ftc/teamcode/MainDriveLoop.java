package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystem.RobotSystem;

public class MainDriveLoop {

    public static void loop(Telemetry telemetry, Gamepad gamepad1){
        if (gamepad1.left_bumper) {
            Robot.robot.setIdle();
        }
        else if (gamepad1.right_bumper){
            Robot.robot.setIntaking();
        }
        else if (gamepad1.a){
            Robot.robot.setStartShooting();
        }

        Robot.ramp.setIdlePower(gamepad1.right_trigger - gamepad1.left_trigger);
        Robot.robot.setOkToFind(gamepad1.b);


        Robot.update(telemetry);


        Robot.robot.doTelemetry(telemetry);
        Robot.shooter.camera.doTelemetry(telemetry, false);
        //Robot.shooter.writeSpeeds(telemetry);
        //telemetry.addData("Ramp Position", Robot.ramp.getRampPosition());
        telemetry.addData("Ball Detected", Robot.ramp.isBallInIntake());
        telemetry.addData("Balls Loaded", Robot.ramp.getBallsLoaded());
        telemetry.addData("Shooter At speed", Robot.shooter.isReadyForShot());
        telemetry.addData("Current Heading", Robot.odometry.getHeading());
        //telemetry.addData(Robot.robot.shootingSystem.)
        telemetry.update();
    }
}
