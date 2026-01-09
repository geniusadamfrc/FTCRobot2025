package org.firstinspires.ftc.teamcode.subsystem.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystem.CommandSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.Subsystem;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import java.util.Locale;


public class Drivetrain extends CommandSubsystem {
    public static final String leftFrontName = "Front Left Motor";
    public static final String rightFrontName = "Front Right Motor";
    public static final String leftBackName = "Back Left Motor";
    public static final String rightBackName = "Back Right Motor";
    public DcMotorEx leftFrontDrive   = null;
    public DcMotorEx  rightFrontDrive  = null;
    public DcMotorEx  leftBackDrive   = null;
    public DcMotorEx  rightBackDrive  = null;

    public void init(HardwareMap hardwareMap){
        initMotors(hardwareMap);
    }
    private void initMotors(HardwareMap hardwareMap){
        leftFrontDrive  = hardwareMap.get(DcMotorEx.class, leftFrontName); //0
        rightFrontDrive = hardwareMap.get(DcMotorEx.class, rightFrontName); //3
        leftBackDrive  = hardwareMap.get(DcMotorEx.class, leftBackName); //1
        rightBackDrive = hardwareMap.get(DcMotorEx.class, rightBackName); //2

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left and right sticks forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
    }





    public void setBrakeMode(){
        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void setCoastMode(){
        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    public void setDriveToZero(){
        driveRobotRelative(0,0,0);
    }
    public void driveRobotRelative(double forward, double turn, double strafe){
        double fls = forward + turn + strafe;
        double frs = forward - turn - strafe;
        double bls = forward + turn - strafe;
        double brs = forward - turn + strafe;
         
        double maxSpeed = computeMaxSpeed(fls, frs, bls, brs);
        double minSpeed = computeMinSpeed(fls, frs, bls, brs);
        maxSpeed = Math.max(Math.abs(maxSpeed), Math.abs(minSpeed));
        
        if(maxSpeed>1){
            fls = fls/maxSpeed;
            frs = frs/maxSpeed;
            bls = bls/maxSpeed;
            brs /= maxSpeed;
        }
        setPowersRaw(fls, frs, bls,brs);
        
    }
    private double computeMaxSpeed(double fls, double frs, double bls, double brs){
        double maxSpeed;
        maxSpeed = Math.max(frs, fls);
        maxSpeed = Math.max(maxSpeed, bls);
        maxSpeed = Math.max(maxSpeed, brs);
        return maxSpeed;
    }
    private double computeMinSpeed(double fls, double frs, double bls, double brs){
        double minSpeed = Math.min(frs, fls);
        minSpeed = Math.min(minSpeed, bls);
        minSpeed = Math.min(minSpeed, brs);
        return minSpeed;
    }
    public void driveFieldRelative(double x, double y, double spin, double currentHeadingInRadians){
        double strafe = Math.cos(currentHeadingInRadians)*x + Math.sin(currentHeadingInRadians)*y;
        double forward = Math.sin(currentHeadingInRadians)*x + Math.cos(currentHeadingInRadians)*y;;
        driveRobotRelative(forward, spin, strafe);
    }
    public void setPowersRaw(double fls, double frs, double bls, double brs){
        leftFrontDrive.setPower(fls);
        rightFrontDrive.setPower(frs);
        leftBackDrive.setPower(bls);
        rightBackDrive.setPower(brs);
        
    }
    public int getEncoderReading(){
        return (leftFrontDrive.getCurrentPosition() 
            + leftBackDrive.getCurrentPosition()
            + rightFrontDrive.getCurrentPosition()
            + rightBackDrive.getCurrentPosition())/4;
    }


}