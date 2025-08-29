package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class SlideClaw extends Subsystem {
    public Servo clawLeft;
    public Servo clawRight;
    public Servo clawLever;
   
    
    public void init(HardwareMap hardwareMap){
        //fix this
        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");
        clawLever = hardwareMap.get(Servo.class, "clawLever");
        
     }
    
    public void playOnceImpl(){
        closeClaw();
        lowerClaw();
    }
    public void closeClaw(){
        clawLeft.setPosition(0.0);
        clawRight.setPosition(0.35);
    }
    public void openClaw(){
        clawLeft.setPosition(1.0);
        clawRight.setPosition(0.0);
    }
    public void raiseClaw(){
        clawLever.setPosition(0.0);
    }
    public void lowerClaw(){
        clawLever.setPosition(1.0);
    }
}