package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.commands.CommandManager;
import org.firstinspires.ftc.teamcode.commands.simple.drive.ManualRobotRelativeMecanumDrive;
import org.firstinspires.ftc.teamcode.gamepad.GamePadExtended;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Ramp;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Odometry;
import org.firstinspires.ftc.teamcode.subsystem.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain;

public class Robot {
    //These are default.  Don't touch.
    public static GamePadExtended gamepadex1;
    public static GamePadExtended gamepadex2;
    //update these
    public static Drivetrain drivetrain;
    public static Odometry odometry;
    public static Shooter shooter;
    public static Ramp ramp;
    public static Intake intake;
    public static Pose2D lastPosition = new Pose2D(DistanceUnit.MM, 0.0, 0.0, AngleUnit.DEGREES, 0.0);


    public static void init(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2, Pose2D initialPose){
        //These are default.  Don't touch.
        if (gamepadex1 == null) gamepadex1 = new GamePadExtended(gamepad1);
        if (gamepadex2 == null) gamepadex2 = new GamePadExtended(gamepad2);

        //update these
        if (drivetrain == null) drivetrain = new Drivetrain();
        drivetrain.init(hardwareMap, telemetry, initialPose);
        if (odometry == null) odometry = new Odometry();
        odometry.init(hardwareMap, telemetry, initialPose);
        if (shooter == null) shooter = new Shooter();
        shooter.init(hardwareMap);
        if (ramp == null) ramp = new Ramp();
        ramp.init(hardwareMap);
        if (intake == null) intake = new Intake();
        intake.init(hardwareMap);

    }
    public static void init(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2) {
        init(hardwareMap, telemetry, gamepad1, gamepad2, lastPosition);
    }



    public static void update(){
        //These are default.  Don't touch.
        gamepadex1.update();
        gamepadex2.update();
        CommandManager.update();

        //update these
        drivetrain.loop();
        odometry.loop();
        shooter.loop();
        ramp.loop();
        intake.loop();
        lastPosition = odometry.getOdoPosition();
    }


    public static void setupParams(int i) {
        Robot.shooter.camera.setGoalId(20);
        try {
            CommandManager.registerDefaultCommand(new ManualRobotRelativeMecanumDrive(Robot.gamepadex1.left_stick_y, Robot.gamepadex1.left_stick_x, Robot.gamepadex1.right_stick_x), Robot.drivetrain);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }


    }
}
