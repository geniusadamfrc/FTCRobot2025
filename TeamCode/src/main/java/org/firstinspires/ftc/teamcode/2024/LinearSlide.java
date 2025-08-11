package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class LinearSlide extends Subsystem{
    public DcMotor linearSlide;
    public final int MAX_DISTANCE = 770;
    public final int MIN_DISTANCE = 100;
    

    public double startingPosition;
    
    public void init(HardwareMap hardwareMap){
        linearSlide = hardwareMap.get(DcMotor.class, "linearSlide");
        linearSlide.setDirection(DcMotor.Direction.REVERSE);
        linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        startingPosition = linearSlide.getCurrentPosition();
    }
    public void manuallyMove(double speed){
        if (speed < 0.04 && speed > -0.04 && state==SubsystemState.COMMANDED)
            return;
        if (state!=SubsystemState.IDLE)
            forceExit();
        if(linearSlide.getMode() != DcMotor.RunMode.RUN_USING_ENCODER)
            linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (linearSlide.getCurrentPosition() -startingPosition < 100 && speed <-0.01){
            speed = 0.0;
        }
        if (linearSlide.getCurrentPosition() -startingPosition > MAX_DISTANCE && speed > 0.01){
            speed =0.0;
        }
        linearSlide.setPower(speed);
    }
    public double commandMove(double speed){
        if (state!=SubsystemState.COMMANDED) return 0.0;
        if (linearSlide.getCurrentPosition() -startingPosition < 100 && speed <-0.01){
            speed = 0.0;
        }
        if (linearSlide.getCurrentPosition() -startingPosition > MAX_DISTANCE && speed > 0.01){
            speed =0.0;
        }
        linearSlide.setPower(speed);
        return speed;
    }
    public void extendToPosition(int position){
        
        if(position>MAX_DISTANCE){
             position=MAX_DISTANCE;
        }
        if(position<MIN_DISTANCE){
             position=MIN_DISTANCE;
        }
        
        linearSlide.setPower(position > linearSlide.getCurrentPosition() ? 0.5 : -0.5);
        linearSlide.setTargetPosition(position);
    }
    
}   