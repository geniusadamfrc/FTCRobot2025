package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Ramp extends Subsystem{
    public static final String RAMP_NAME = "ramp";
    public static final double DEFAULT_TARGET_POWER = 0.5;
    private DcMotorEx rampMotor;

    private double targetPower;
    private int ballsLoaded;
    private RampState rampState;
    public void init (HardwareMap hardwareMap){
        rampMotor = hardwareMap.get(DcMotorEx.class, RAMP_NAME);
        rampMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rampState = RampState.IDLE;
        targetPower = DEFAULT_TARGET_POWER;
    }
    public void setTargetPower(double power){
        this.targetPower = power;
    }
    public double getRampPosition() {
        return rampMotor.getCurrentPosition();
    }

    public void setIdleRamp(){
        rampState = RampState.IDLE;
        doIdle();
    }
    public void setSlowRamp(){
        rampState = RampState.SLOW_RAMP;
        doSlowRamp();
    }
    public void setFeeding(){
        rampState = RampState.FEEDING;
    }



    public void ballLoaded(){ballsLoaded++;}
    public void ballUnloaded(){ballsLoaded--;}
    public void resetBallsLoaded(){ ballsLoaded=0;}
    public int getBallsLoaded(){return ballsLoaded;}



    @Override
    public void loop(){
        if (rampState == RampState.IDLE) doIdle();
        else if (rampState == RampState.SLOW_RAMP) doSlowRamp();
        else if (rampState == RampState.FEEDING) doFeeding();
    }
    private void doIdle() {
        rampMotor.setPower(0.0);
    }
    private void doSlowRamp()   {
        rampMotor.setPower(targetPower);
    }
    private void doFeeding() {
        rampMotor.setPower(targetPower);
    }



    public enum RampState{
        IDLE,  SLOW_RAMP, FEEDING
    }

}
