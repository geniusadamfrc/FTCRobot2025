package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.CommandManager;
import org.firstinspires.ftc.teamcode.commands.simple.drive.AlignTargetOdo;

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
    private Command alignTarget = null;

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
            Robot.intake.setIdleIntake();
             Robot.ramp.setIdleRamp();
             intaking = false;
        }
        if (gamepad1.right_bumper){
            if (!intaking){
                intaking = true;
                Robot.intake.setIntaking();
                Robot.ramp.setLoading();
            }
        }

        /*
        if (gamepad1.left_trigger >0.05 || gamepad1.right_trigger > 0.05){
            Robot.ramp.setTargetPower(gamepad1.right_trigger - gamepad1.left_trigger);
            Robot.ramp.setFeeding();
        }else{
            Robot.ramp.setIdleRamp();
        }
        */



        if (gamepad1.b) {
            if (alignTarget == null) {
                alignTarget = new AlignTargetOdo(false);
                CommandManager.schedule(alignTarget);
            }
        } else {
            if (alignTarget != null) {
                alignTarget.finish();
                alignTarget = null;
            }
        }
        telemetry.addData("Ramp State", Robot.ramp.getState());
        Robot.shooter.camera.doTelemetry(telemetry, false);
        Robot.shooter.writeSpeeds(telemetry);
        telemetry.addData("Drivetrain: ", Robot.drivetrain.getCurrentCommand());
        telemetry.addData("Ramp Position", Robot.ramp.getRampPosition());
        Robot.update();
        telemetry.addData("Bottom IR Break", Robot.ramp.getBottomIRState());
        telemetry.addData("Upper IR Break", Robot.ramp.getUpperIRState());
        telemetry.update();
        //if (gamepad1.x)intakeSpeed = - intakeSpeed;
    }
}