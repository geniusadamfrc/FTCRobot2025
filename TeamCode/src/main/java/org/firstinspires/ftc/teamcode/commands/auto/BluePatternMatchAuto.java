package org.firstinspires.ftc.teamcode.commands.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.CommandManager;
import org.firstinspires.ftc.teamcode.commands.ParallelCommand;
import org.firstinspires.ftc.teamcode.commands.SequentialCommand;
import org.firstinspires.ftc.teamcode.commands.simple.DriveForwardAndIntake;
import org.firstinspires.ftc.teamcode.commands.simple.Shoot3Balls;
import org.firstinspires.ftc.teamcode.commands.simple.WaitMSeconds;
import org.firstinspires.ftc.teamcode.commands.simple.drive.AlignTargetOdo;
import org.firstinspires.ftc.teamcode.commands.simple.drive.DriveStraightPath;
import org.firstinspires.ftc.teamcode.commands.simple.drive.ManualRobotRelativeMecanumDrive;
import org.firstinspires.ftc.teamcode.commands.simple.drive.MoveToPointOnField;
import org.firstinspires.ftc.teamcode.commands.simple.drive.MoveToPointOnFieldWithBackup;
import org.firstinspires.ftc.teamcode.commands.simple.drive.MoveToPointOnFieldWithUpdate;
import org.firstinspires.ftc.teamcode.commands.simple.drive.Turn90DegreesPath;
import org.firstinspires.ftc.teamcode.commands.simple.shooter.IdentifyPattern;
import org.firstinspires.ftc.teamcode.commands.simple.shooter.SpeedUpForShooting;

@Autonomous(name="Blue Pattern Match Auto")
public class BluePatternMatchAuto extends OpMode {

    private SequentialCommand main;
    @Override
    public void init() {
        //Robot.init(hardwareMap, telemetry, gamepad1, gamepad2, new Pose2D(DistanceUnit.MM,0, 0, AngleUnit.DEGREES,0));

        Robot.init(hardwareMap, telemetry, gamepad1, gamepad2, new Pose2D(DistanceUnit.INCH,-59, 58, AngleUnit.DEGREES,-35));
        Robot.shooter.camera.setGoalId(20);
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
        ParallelCommand sq1 = new ParallelCommand();
        sq1.addCommand(new DriveStraightPath(Robot.drivetrain, Robot.drivetrain.roadRunnerController, -24));
        sq1.addCommand(new SpeedUpForShooting(620));
        main.addCommand(sq1);
        main.addCommand(new Shoot3Balls().init(Robot.shooter, Robot.ramp));
        main.addCommand(new Turn90DegreesPath(-115));
        main.addCommand(new IdentifyPattern());
        //main.addCommand(new Turn90Degrees(0.4, 170, telemetry ));


        main.addCommand(new MoveToPointOnFieldWithUpdate(-12,25,179));
        main.addCommand(new DriveForwardAndIntake(-30));
        ParallelCommand sq2 = new ParallelCommand();
        sq2.addCommand(new MoveToPointOnFieldWithBackup(-20, 32, -35));
        sq2.addCommand(new SpeedUpForShooting(620));
        main.addCommand(sq2);
        main.addCommand(new WaitMSeconds(200));
        main.addCommand(new AlignTargetOdo(true));
        main.addCommand(new Shoot3Balls().init(Robot.shooter, Robot.ramp));
        main.addCommand(new MoveToPointOnField(-12, 0, 179));
        //main.addCommand(new DriveForwardAndIntake(-30));
        /*
        ParallelCommand sq3 = new ParallelCommand();
        sq3.addCommand(new MoveToPointOnField(-20, 32, -35));
        sq3.addCommand(new SpeedUpForShooting(620));
        main.addCommand(sq3);
        main.addCommand(new WaitMSeconds(200));
        main.addCommand(new AlignTargetOdo(true));
        main.addCommand(new Shoot3Balls().init(Robot.shooter, Robot.ramp));
        */
        //main.addCommand(new Turn90Degrees(Robot.drivetrain, 0.3, 90, telemetry));
        //main.addCommand(new DriveStraight(Robot.drivetrain, 0.0, 0.5, 300, telemetry));
        main.begin();
    }

    @Override
    public void loop() {
        Robot.update(telemetry);
        Robot.drivetrain.writeOutPosition(telemetry);
        telemetry.addData("Pattern ID", Robot.shooter.camera.getPatternId());
        if (!main.isFinished())
            main.loop();
        Robot.lastPosition = Robot.drivetrain.getOdoPosition();
        Robot.shooter.writeSpeeds(telemetry);
        Robot.drivetrain.writeOutPosition(telemetry);
    }
}
