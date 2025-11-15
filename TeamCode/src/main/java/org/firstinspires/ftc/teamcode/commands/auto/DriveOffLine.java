package org.firstinspires.ftc.teamcode.commands.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.CommandManager;
import org.firstinspires.ftc.teamcode.commands.SequentialCommand;
import org.firstinspires.ftc.teamcode.commands.simple.DriveStraight;
import org.firstinspires.ftc.teamcode.commands.simple.DriveStraightDistance;
import org.firstinspires.ftc.teamcode.commands.simple.ManualRobotRelativeMecanumDrive;
import org.firstinspires.ftc.teamcode.commands.simple.Shoot3Balls;
import org.firstinspires.ftc.teamcode.commands.simple.Turn90Degrees;

@Autonomous(name="Drive Off Line")
public class DriveOffLine extends OpMode {
    private SequentialCommand main;
    @Override
    public void init() {
        Robot.init(hardwareMap, telemetry, gamepad1, gamepad2);
        try {
            CommandManager.registerDefaultCommand(new ManualRobotRelativeMecanumDrive(Robot.gamepadex1.left_stick_y, Robot.gamepadex1.left_stick_x, Robot.gamepadex1.right_stick_x), Robot.drivetrain);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void start(){
        main = new SequentialCommand();
        main.addCommand(new DriveStraight(Robot.drivetrain, 0.5, 0.0, 500, telemetry));
        main.begin();
    }

    @Override
    public void loop() {
        if (!main.isFinished())
            main.loop();
    }
}
