package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter extends Subsystem{
    public final static String LEFT_SHOOTER_NAME = "leftShooter";
    public final static String RIGHT_SHOOTER_NAME = "rightShooter";

    public final static double SPEED_DROP_ON_SHOT = 200.0;

    private DcMotorEx leftShooter;
    private DcMotorEx rightShooter;

    private double targetSpeed;
    private double speedTolerance;

    private ShooterState shooterState;

    public void init(HardwareMap hardwareMap){
        leftShooter = hardwareMap.get(DcMotorEx.class, LEFT_SHOOTER_NAME);
        rightShooter = hardwareMap.get(DcMotorEx.class, RIGHT_SHOOTER_NAME);

        leftShooter.setDirection(DcMotorSimple.Direction.FORWARD);
        rightShooter.setDirection(DcMotorSimple.Direction.REVERSE);
        leftShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }


    public void setTargetSpeed(double targetSpeed){
        this.targetSpeed = targetSpeed;
        leftShooter.setVelocity(targetSpeed);
        rightShooter.setVelocity(targetSpeed);
    }
    public void setSpeedTolerance(double speedTolerance){
        this.speedTolerance = speedTolerance;
    }
    public void writeSpeeds(Telemetry telemetry){
        telemetry.addData("Left Speed", leftShooter.getVelocity());
        telemetry.addData("Right Speed", rightShooter.getVelocity());
    }
    public boolean isAtSpeed(){
        return isAtSpeed(this.speedTolerance);
    }
    public boolean isAtSpeed (double speedTolerance){
        return leftShooter.getVelocity() < targetSpeed+ speedTolerance && leftShooter.getVelocity() > targetSpeed - speedTolerance &&
                rightShooter.getVelocity() < targetSpeed + speedTolerance && rightShooter.getVelocity() > targetSpeed - speedTolerance;

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

    public void setIdle(){
        setTargetSpeed(0.0);
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


    private enum ShooterState {
        IDLE, SPINNING_UP, READY_FOR_SHOT, BALL_SHOT_SPINNING_UP, MANUAL_CONTROL
    }

}
