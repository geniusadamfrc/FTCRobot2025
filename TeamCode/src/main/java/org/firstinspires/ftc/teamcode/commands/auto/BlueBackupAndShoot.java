package org.firstinspires.ftc.teamcode.commands.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.CommandManager;
import org.firstinspires.ftc.teamcode.commands.SequentialCommand;
import org.firstinspires.ftc.teamcode.commands.simple.drive.DriveStraight;
import org.firstinspires.ftc.teamcode.commands.simple.drive.DriveStraightPath;
import org.firstinspires.ftc.teamcode.commands.simple.drive.ManualRobotRelativeMecanumDrive;
import org.firstinspires.ftc.teamcode.commands.simple.Shoot3Balls;
import org.firstinspires.ftc.teamcode.commands.simple.drive.MoveToPointOnField;
import org.firstinspires.ftc.teamcode.commands.simple.drive.Turn90DegreesPath;
import org.firstinspires.ftc.teamcode.commands.simple.shooter.IdentifyPattern;

@Autonomous(name="Blue Backup And Shoot")
public class BlueBackupAndShoot extends OpMode {

    private SequentialCommand main;
    @Override
    public void init() {
        //Robot.init(hardwareMap, telemetry, gamepad1, gamepad2, new Pose2D(DistanceUnit.MM,0, 0, AngleUnit.DEGREES,0));

        Robot.init(hardwareMap, telemetry, gamepad1, gamepad2, new Pose2D(DistanceUnit.INCH,-60, 60, AngleUnit.DEGREES,-45));
        try {
            CommandManager.registerDefaultCommand(new ManualRobotRelativeMecanumDrive(Robot.gamepadex1.left_stick_y, Robot.gamepadex1.left_stick_x, Robot.gamepadex1.right_stick_x), Robot.drivetrain);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }

    }
    @Override
    public void init_loop(){
        Robot.drivetrain.writeOutPosition(telemetry);
    }

    @Override
    public void start(){
        main = new SequentialCommand();
        main.addCommand(new DriveStraightPath(Robot.drivetrain, Robot.drivetrain.roadRunnerController, -24));
        main.addCommand(new Shoot3Balls().init(Robot.shooter, Robot.ramp));
        main.addCommand(new Turn90DegreesPath(90));
        main.addCommand(new IdentifyPattern());
        main.addCommand(new MoveToPointOnField(-12,12,180));
        //main.addCommand(new Turn90Degrees(Robot.drivetrain, 0.3, 90, telemetry));
        main.addCommand(new DriveStraight(Robot.drivetrain, 0.0, 0.5, 300, telemetry));
        main.begin();
    }

    @Override
    public void loop() {
        Robot.drivetrain.writeOutPosition(telemetry);
        if (!main.isFinished())
            main.loop();

    }
}
