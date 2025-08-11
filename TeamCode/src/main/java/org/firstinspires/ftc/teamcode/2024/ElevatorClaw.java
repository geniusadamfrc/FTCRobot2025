package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class ElevatorClaw extends Subsystem{
    public Servo claw;
    public Servo arm;
    
    public void init(HardwareMap hardwareMap){
        //fix this
        claw = hardwareMap.get(Servo.class, "claw");
        arm = hardwareMap.get(Servo.class, "arm");
        closeClaw();
        raiseArm();
     }
    
    public void playOnce(){
        centerArm();
    }
    

    public void lowerArm(){
        arm.setPosition(0.0);
    }
    public void centerArm() {
        arm.setPosition(0.5);
    }
    public void raiseArm(){
        arm.setPosition(1.0);
    }
    public void setArmPosition(double position){
        arm.setPosition(position);
    }
    
    public void openClaw(){
        claw.setPosition(0.5);
    }
    public void closeClaw(){
        claw.setPosition(1.0);      
    }    
    
}