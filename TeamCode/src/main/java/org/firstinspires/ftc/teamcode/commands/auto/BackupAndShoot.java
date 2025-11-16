package org.firstinspires.ftc.teamcode.commands.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.CommandManager;
import org.firstinspires.ftc.teamcode.commands.SequentialCommand;
import org.firstinspires.ftc.teamcode.commands.simple.DriveStraight;
import org.firstinspires.ftc.teamcode.commands.simple.DriveStraightDistance;
import org.firstinspires.ftc.teamcode.commands.simple.DriveStraightPath;
import org.firstinspires.ftc.teamcode.commands.simple.ManualRobotRelativeMecanumDrive;
import org.firstinspires.ftc.teamcode.commands.simple.Shoot3Balls;
import org.firstinspires.ftc.teamcode.commands.simple.Turn90Degrees;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Autonomous(name="Auto Backup And Shoot")
public class BackupAndShoot extends OpMode {

    private SequentialCommand main;
    @Override
    public void init() {
        Robot.init(hardwareMap, telemetry, gamepad1, gamepad2);
        try {
            CommandManager.registerDefaultCommand(new ManualRobotRelativeMecanumDrive(Robot.gamepadex1.left_stick_y, Robot.gamepadex1.left_stick_x, Robot.gamepadex1.right_stick_x), Robot.drivetrain);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
        Robot.drivetrain.setOdoPositions(3352, 3200, 45);

    }
    @Override
    public void start(){
        main = new SequentialCommand();
        main.addCommand(new DriveStraightPath(Robot.drivetrain, new MecanumDrive(hardwareMap, new Pose2d(0.0, 0.0, 0.0)), 32));
        main.addCommand(new Shoot3Balls().init(Robot.shooter, Robot.ramp));
        //main.addCommand(new Turn90Degrees(Robot.drivetrain, 0.3, 90, telemetry));
        main.addCommand(new DriveStraight(Robot.drivetrain, 0.0, 0.5, 300, telemetry));
        main.begin();
    }

    @Override
    public void loop() {
        if (!main.isFinished())
            main.loop();

    }
}
