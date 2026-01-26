package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.RobotSystem;

@TeleOp(name = "Blue Teleop")
public class BlueTeleopTest extends OpMode {


    @Override
    public void init(){

        Robot.init(hardwareMap, telemetry, gamepad1, gamepad2);
        Robot.setupParams(20, -45);
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch START to start OpMode");
        telemetry.update();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }


    @Override
    public void loop() {
        MainDriveLoop.loop(telemetry, gamepad1);
    }
}