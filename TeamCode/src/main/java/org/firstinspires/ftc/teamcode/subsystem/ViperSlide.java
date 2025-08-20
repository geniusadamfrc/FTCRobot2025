package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class ViperSlide extends Subsystem {

    // todo: write your code here
   
    public DcMotor elevator;
    
    //need to update
    public final int MIN_POSITION = 100;
    public final int MAX_POSITION = 10000;
    public final int TRANSFER_HEIGHT = 880; //update this

    private double currentPower = 0.0;
    private int startingPosition;
    
    public void init(HardwareMap hardwareMap){
        //fix this
        elevator = hardwareMap.get(DcMotor.class, "elevator");
        elevator.setDirection(DcMotor.Direction.REVERSE);
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        startingPosition = elevator.getCurrentPosition();
     }
    
    public void playOnce(){
    }
    
    public void raiseElevator(double speed){
        if (speed < 0.04 && speed > -0.04 && elevator.getMode() != DcMotor.RunMode.RUN_USING_ENCODER)
            return;
        if (isBusy())
            forceExit();
        if(elevator.getMode() != DcMotor.RunMode.RUN_USING_ENCODER)
            elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (elevator.getCurrentPosition()-startingPosition > MAX_POSITION  && speed >0.01) 
            speed = 0.0;
        if (elevator.getCurrentPosition()-startingPosition < MIN_POSITION  && speed <-0.01) 
            speed = 0.0;
        elevator.setPower(speed);
    }
    public void raiseElevatorUnrestricted(double speed){
        elevator.setPower(speed);
        if (getPosition() <0) startingPosition = elevator.getCurrentPosition(); 
    }
    
    public void resetEncoders(){
        //startingPosition = elevator.getCurrentPosition();
    }
    
    public void moveElevatorToHeight(int height){
        moveElevatorToHeight(height,0.5);
    }
    public void moveElevatorToHeight(int height, double speed){
        elevator.setTargetPosition(height+startingPosition);
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        currentPower = getPosition() > height ? -speed : speed;
        elevator.setPower(currentPower);
    }
    public boolean isElevatorBusy(){
        return elevator.isBusy();
    }
    
    
    public void loop(){
    }
    
    public int getPosition(){
        return elevator.getCurrentPosition() - startingPosition;
    }
    public int getRawPosition(){ return elevator.getCurrentPosition();
    }
}