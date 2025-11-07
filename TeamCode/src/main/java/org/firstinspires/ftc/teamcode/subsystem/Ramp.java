package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Ramp extends Subsystem{
    private DcMotorEx rampMotor;
    private DcMotorEx intakeMotor;
    public static final String RAMP_NAME = "ramp";
    public static final String INTAKE_NAME = "intake";


    private RampState rampState;
    public void init (HardwareMap hardwareMap){
        rampMotor = hardwareMap.get(DcMotorEx.class, RAMP_NAME);
        intakeMotor = hardwareMap.get(DcMotorEx.class, INTAKE_NAME);
        rampState = RampState.IDLE;
    }


    public void setRampPower(double power){
        rampMotor.setPower(power);
    }
    public void setIntakePower (double power){
        intakeMotor.setPower(power);
    }

    public void setIdle(){
        rampState = RampState.IDLE;
    }
    public void start(){
        rampState = RampState.LOADING_EMPTY;
    }
    public void startLoaded(){
        rampState = RampState.LOADED;
    }






   public enum RampState{
        LOADING_EMPTY, LOADING_1, LOADING_2, LOADED, IDLE
   }
}
