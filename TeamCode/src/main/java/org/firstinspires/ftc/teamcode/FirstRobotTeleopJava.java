package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "FirstRobot_Teleop")
public class FirstRobotTeleopJava extends LinearOpMode {

  private Servo Grabber;
  private DcMotor LeftWheeler;
  private DcMotor RightWheeler;
  private DcMotor ArmMover;

  /**
   * This function is executed when this OpMode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    Grabber = hardwareMap.get(Servo.class, "Grabber");
    LeftWheeler = hardwareMap.get(DcMotor.class, "LeftWheeler");
    RightWheeler = hardwareMap.get(DcMotor.class, "RightWheeler");
    ArmMover = hardwareMap.get(DcMotor.class, "ArmMover");

    // Put initialization blocks here.
    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      while (opModeIsActive()) {
        // Put loop blocks here.
        telemetry.update();
        telemetry.addData("Left Stick", gamepad1.left_stick_y);
        telemetry.addData("Claw", Grabber.getPosition());
        LeftWheeler.setPower(gamepad1.left_stick_y * -1);
        RightWheeler.setPower(gamepad1.left_stick_y);
        LeftWheeler.setPower(gamepad1.left_stick_x);
        RightWheeler.setPower(gamepad1.left_stick_x);
        ArmMover.setPower(gamepad1.left_trigger - gamepad1.right_trigger);
        if (gamepad1.a) {
          Grabber.setPosition(0);
        }
        if (gamepad1.b) {
          Grabber.setPosition(1);
        }
      }
    }
  }
}