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

    public final static double SPEED_DROP_ON_SHOT = 80.0;
    public final static double DEFAULT_TARGET_SPEED = 620.0;
    public final static double DEFAULT_SPEED_TOLERANCE = 30.0;
    public final static int ITERATIONS_TO_ASSUME_AT_SPEED = 4;

    private DcMotorEx leftShooter;
    private DcMotorEx rightShooter;

    private double targetSpeed;
    private double speedTolerance;
    private int iterationsForSpeed;

    private boolean ballShot;
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
        this.speedTolerance = DEFAULT_SPEED_TOLERANCE;
        ballShot = false;
    }

    public void setSpeedTolerance(double speedTolerance){
        this.speedTolerance = speedTolerance;
    }
    public void writeSpeeds(Telemetry telemetry){
        telemetry.addData("Target Speed", targetSpeed);
        telemetry.addData("Actual Speeds L/R", leftShooter.getVelocity() + "/" + rightShooter.getVelocity());
        //telemetry.addData("Right Speed", rightShooter.getVelocity());
    }

    public void setIdleShooter(){
        shooterState = ShooterState.IDLE;
    }
    public void startShooting(){
        startShooting(DEFAULT_TARGET_SPEED);
    }
    public void startShooting(double defaultSpeed){
        shooterState = ShooterState.SPINNING_UP;
        targetSpeed = defaultSpeed;
        ballShot = false;
        doSpinningUp();
    }
    public boolean isReadyForShot(){
        return shooterState == ShooterState.READY_FOR_SHOT;
    }


    @Override
    public void loop(){
        if (shooterState == ShooterState.IDLE) doIdle();
        else if (shooterState == ShooterState.SPINNING_UP) doSpinningUp();
        else if (shooterState == ShooterState.READY_FOR_SHOT) doReadyForShot();
    }

    private void doIdle(){
        leftShooter.setPower(0.0);
        rightShooter.setPower(0.0);
    }
    private void doSpinningUp(){
        computeTargetSpeed();
        if (isAtSpeed()) {
            if (iterationsForSpeed >= ITERATIONS_TO_ASSUME_AT_SPEED) {
                shooterState = ShooterState.READY_FOR_SHOT;
                doReadyForShot();
            }else {
                iterationsForSpeed++;
            }
        }else{
            iterationsForSpeed = 0;
        }
        leftShooter.setVelocity(targetSpeed);
        rightShooter.setVelocity(targetSpeed);

    }
    private void doReadyForShot(){
        iterationsForSpeed = 0;
        if (!isAtSpeed(SPEED_DROP_ON_SHOT)) ballShot = true;
        computeTargetSpeed();
        if (!isAtSpeed()) shooterState = ShooterState.SPINNING_UP;
    }

    private void computeTargetSpeed(){
        double cameraSpeed = getIdealShootingSpeed();
        if (cameraSpeed>100){
            targetSpeed = cameraSpeed;
        }
    }
    private boolean isAtSpeed(){
        return isAtSpeed(this.speedTolerance);
    }
    public boolean isAtSpeed(double speedTolerance){
        return leftShooter.getVelocity() < targetSpeed+ speedTolerance && leftShooter.getVelocity() > targetSpeed - speedTolerance &&
                rightShooter.getVelocity() < targetSpeed + speedTolerance && rightShooter.getVelocity() > targetSpeed - speedTolerance;

    }
    private double getIdealShootingSpeed()  {
        double range = camera.computeRangeToGoal(false);
        if (range < 30) return 0;
        else if (range< 71) return 600;
        else {
            return 330 + 4.0 * range;
        }
        //70*2.5 +5 = 600
        //113*2.5 + 500 = 780
        // 105/43
    }



    private enum ShooterState {
        IDLE, SPINNING_UP, READY_FOR_SHOT, MANUAL_CONTROL
    }
}
