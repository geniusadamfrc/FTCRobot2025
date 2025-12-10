package org.firstinspires.ftc.teamcode.subsystem.shooter;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.subsystem.Subsystem;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Shooter extends Subsystem {
    public final static String LEFT_SHOOTER_NAME = "leftShooter";
    public final static String RIGHT_SHOOTER_NAME = "rightShooter";

    public final static double SPEED_DROP_ON_SHOT = 200.0;



    private DcMotorEx leftShooter;
    private DcMotorEx rightShooter;

    private double targetSpeed;
    private double speedTolerance;

    private ShooterState shooterState;
    public CameraSystem camera;

    public void init(HardwareMap hardwareMap){
        leftShooter = hardwareMap.get(DcMotorEx.class, LEFT_SHOOTER_NAME);
        rightShooter = hardwareMap.get(DcMotorEx.class, RIGHT_SHOOTER_NAME);

        leftShooter.setDirection(DcMotorSimple.Direction.FORWARD);
        rightShooter.setDirection(DcMotorSimple.Direction.REVERSE);
        leftShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftShooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(56, 0.8,0,10));
        rightShooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(56, 0.8,0,10));
        camera = new CameraSystem();
        camera.init(hardwareMap);
    }

    public void setTargetSpeed(double targetSpeed){
        //leftShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //rightShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.targetSpeed = targetSpeed;
        leftShooter.setVelocity(targetSpeed);
        rightShooter.setVelocity(targetSpeed);
    }
    public void setPower(double power){
        leftShooter.setPower(power);
        rightShooter.setPower(power);
    }
    public void setSpeedTolerance(double speedTolerance){
        this.speedTolerance = speedTolerance;
    }
    public void writeSpeeds(Telemetry telemetry){
        telemetry.addData("Actual Speeds L/R", leftShooter.getVelocity() + "/" + rightShooter.getVelocity());
        //telemetry.addData("Right Speed", rightShooter.getVelocity());
    }
    public boolean isAtSpeed(){
        return isAtSpeed(this.speedTolerance);
    }
    public boolean isAtSpeed (double speedTolerance){
        return leftShooter.getVelocity() < targetSpeed+ speedTolerance && leftShooter.getVelocity() > targetSpeed - speedTolerance &&
                rightShooter.getVelocity() < targetSpeed + speedTolerance && rightShooter.getVelocity() > targetSpeed - speedTolerance;

    }
    public double getIdealShootingSpeed()  {
            double range = camera.computeRangeToGoal();
            if (range < 30) return 0;
            else if (range< 51) return 600;
            else {
                return 500 + 2.5 * range;
            }

    }



    @Override
    public void loop(){
        if ((shooterState == ShooterState.SPINNING_UP|| shooterState == ShooterState.BALL_SHOT_SPINNING_UP) && isAtSpeed()){
            shooterState = ShooterState.READY_FOR_SHOT;
        }
        if (shooterState == ShooterState.READY_FOR_SHOT && isAtSpeed(SPEED_DROP_ON_SHOT)){
            shooterState = ShooterState.BALL_SHOT_SPINNING_UP;
        }

    }




    public void setShooterIdle(){
        setPower(0.0);
        shooterState = ShooterState.IDLE;
    }
    public void setStartShooting(double targetSpeed){
        setTargetSpeed(targetSpeed);
        shooterState = ShooterState.SPINNING_UP;
    }

    public boolean isReadyForShot(){
        return shooterState == ShooterState.READY_FOR_SHOT;
    }
    public boolean isBallShot(){
        return shooterState == ShooterState.BALL_SHOT_SPINNING_UP;
    }

    public double getBearing() {
        return camera.getBearing();
    }


    private enum ShooterState {
        IDLE, SPINNING_UP, READY_FOR_SHOT, BALL_SHOT_SPINNING_UP, MANUAL_CONTROL
    }





}
