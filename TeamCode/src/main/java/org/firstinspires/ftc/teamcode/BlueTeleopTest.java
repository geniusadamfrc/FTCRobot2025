package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.CommandManager;
import org.firstinspires.ftc.teamcode.commands.simple.drive.AlignTargetOdo;
import org.firstinspires.ftc.teamcode.subsystem.RobotSystem;

@TeleOp(name = "Blue Teleop Test")
public class BlueTeleopTest extends OpMode {
    double oldTime = 0;
    double speed = 1000.0;
    double intakeSpeed = 0.8;
    boolean intaking = false;
    private double targetSpeed = 620;
    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    private AlignTargetOdo alignTarget = null;

    @Override
    public void init(){

        Robot.init(hardwareMap, telemetry, gamepad1, gamepad2);
        Robot.setupParams(20);
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
    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        Robot.drivetrain.playOnce();
    }
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        if (gamepad1.left_bumper) {
            Robot.robot.setIdle();
        }
        else if (gamepad1.right_bumper){
            Robot.robot.setIntaking();
        }
        else if (gamepad1.a){
            Robot.robot.setShooting();
        }

        Robot.ramp.setIdlePower(gamepad1.right_trigger - gamepad1.left_trigger);
        RobotSystem.shootingSystem.setOkToFind(gamepad1.b);


        Robot.update();

        telemetry.addData("Robot State", Robot.robot.getStateString());
        RobotSystem.shootingSystem.doTelemetry(telemetry);
        Robot.shooter.camera.doTelemetry(telemetry, false);
        //Robot.shooter.writeSpeeds(telemetry);
        telemetry.addData("Drivetrain: ", Robot.drivetrain.getCurrentCommand());
        //telemetry.addData("Ramp Position", Robot.ramp.getRampPosition());
        telemetry.addData("Ball Detected", Robot.ramp.isBallInIntake());
        telemetry.addData("Balls Loaded", Robot.ramp.getBallsLoaded());
        telemetry.addData("Shooter At speed", Robot.shooter.isReadyForShot());
        telemetry.addData("Current Heading", Robot.odometry.getHeading());
        //telemetry.addData(Robot.robot.shootingSystem.)
        telemetry.update();
        //if (gamepad1.x)intakeSpeed = - intakeSpeed;
    }
}