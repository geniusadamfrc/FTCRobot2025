package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Drivetrain extends Subsystem{
    public DcMotor  leftFrontDrive   = null;
    public DcMotor  rightFrontDrive  = null;
    public DcMotor  leftBackDrive   = null;
    public DcMotor  rightBackDrive  = null;
    public final static double THRESHOLD = 0.04;
    
    public void init(HardwareMap hardwareMap){
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        leftBackDrive  = hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");
        
        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left and right sticks forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        
        
    }
    public void manualDrive(double forward, double turn, double strafe){
        if (state == SubsystemState.COMMANDED){
            if (forward > THRESHOLD || forward < -THRESHOLD ||
                turn > THRESHOLD || turn < -THRESHOLD ||
                strafe > THRESHOLD || strafe < -THRESHOLD){
                forceExit();
            }else {return;} 
        }
        drive(forward, turn, strafe); 
    }
    
    public void drive(double forward, double turn, double strafe){
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
        setSpeedsRaw(fls, frs, bls,brs);
        
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
    
    
    
    public void setSpeedsRaw(double fls, double frs, double bls, double brs){
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
    
    // todo: write your code here
}