/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.subsystem.shooter.CameraSystem;

import org.firstinspires.ftc.teamcode.subsystem.shooter.Shooter;

/*
 * This OpMode executes a Tank Drive control TeleOp a direct drive robot
 * The code is structured as an Iterative OpMode
 *
 * In this mode, the left and right joysticks control the left and right motors respectively.
 * Pushing a joystick forward will make the attached motor drive forward.
 * It raises and lowers the claw using the Gamepad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@TeleOp(name="Shooter Test", group="Robot")
public class ShooterTest extends OpMode{

    /* Declare OpMode members. */
    public DcMotorEx leftShooterWheel   = null;
    public DcMotorEx rightShooterWheel = null;
    public DcMotorEx rampMotor = null;
    public double speed = 600.0;
    boolean tuneP = false;
    boolean tuneI = false;
    boolean tuneD = false;
    boolean tuneF = false;
    CameraSystem camera;
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        // Define and Initialize Motors
        leftShooterWheel  = hardwareMap.get(DcMotorEx.class, Shooter.LEFT_SHOOTER_NAME);
        rightShooterWheel  = hardwareMap.get(DcMotorEx.class,Shooter.RIGHT_SHOOTER_NAME);
        rampMotor  = hardwareMap.get(DcMotorEx.class, "ramp");


        leftShooterWheel.setDirection(DcMotor.Direction.REVERSE);

        leftShooterWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightShooterWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftShooterWheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(27,8,0.0,15));
        rightShooterWheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(27,8,0.0,15));
        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press START.");    //
        camera = new CameraSystem();
        camera.init(hardwareMap);
        camera.setGoalId(20);

    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits START
     */
    @Override
    public void start() {

    }

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @Override
    public void loop() {

        if (gamepad1.left_bumper) {
            leftShooterWheel.setVelocity(speed);
            rightShooterWheel.setVelocity(speed);
        }else{
            leftShooterWheel.setVelocity(0.0);
            rightShooterWheel.setVelocity(0.0);
        }
        if (gamepad1.a) speed-=1;
        if (gamepad1.y) speed +=1;

        if (gamepad1.dpad_up){
            tuneP = true; tuneI = false; tuneD = false; tuneF = false;
        }
        if (gamepad1.dpad_left){
            tuneP = false; tuneI = false; tuneD = true; tuneF = false;
        }
        if (gamepad1.dpad_down){
            tuneP = false; tuneI = true; tuneD = false; tuneF = false;
        }
        if (gamepad1.dpad_right){
            tuneP = false; tuneI = false; tuneD = false; tuneF = true;
        }
        if (gamepad1.x){
            PIDFCoefficients pid1 = rightShooterWheel.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
            if (tuneP) pid1.p = pid1.p*1.05;
            if (tuneI) pid1.i = pid1.i*1.05;
            if (tuneD) pid1.d = pid1.d*1.05;
            if (tuneF) pid1.f = pid1.f*1.05;
            rightShooterWheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pid1);
        }
        if (gamepad1.b){
            PIDFCoefficients pid1 = rightShooterWheel.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
            if (tuneP) pid1.p = pid1.p/1.05;
            if (tuneI) pid1.i = pid1.i/1.05;
            if (tuneD) pid1.d = pid1.d/1.05;
            if (tuneF) pid1.f = pid1.f/1.05;
            rightShooterWheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pid1);
        }

        rampMotor.setPower(gamepad1.left_trigger-gamepad1.right_trigger);

        // Send telemetry message to signify robot running;
        telemetry.addData("Set Power",  "Offset = %.2f", speed);
        telemetry.addData("Actual Left Speed",  "%.2f", leftShooterWheel.getVelocity());
        telemetry.addData("Actual Right Speed",  "%.2f", rightShooterWheel.getVelocity());

        PIDFCoefficients pid = rightShooterWheel.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("P", pid.p);
        telemetry.addData("I", pid.i);
        telemetry.addData("D", pid.d);
        telemetry.addData("F", pid.f);
        if (tuneP) telemetry.addLine("TUNING P");
        if (tuneI) telemetry.addLine("TUNING I");
        if (tuneD) telemetry.addLine("TUNING D");
        if (tuneF) telemetry.addLine("TUNING F");

        telemetry.addData("Range", camera.computeRangeToGoal(true));
        try{telemetry.addData("Bearing", camera.getBearing());}
        catch (Exception ex){}
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
