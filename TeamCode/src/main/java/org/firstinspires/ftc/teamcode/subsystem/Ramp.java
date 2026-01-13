package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Ramp extends Subsystem{
    public static final String RAMP_NAME = "ramp";
    public static final String BOTTOM_IR_NAME = "bottomIRSensor";
    public static final String BOTTOM_IR_2_NAME = "upperIRSensor";
    public static final double DEFAULT_TARGET_POWER = 0.5;
    public static final double SLOW_RAMP_POWER = 0.4;
    private static final double LOADING_DISTANCE = 200;
    private static final double FEED_DISTANCE = 400 ;
    private DcMotorEx rampMotor;

    private double targetPower;
    private double idlePower;
    private int ballsLoaded;
    private double initialPosition;
    private RampState rampState;
    private DigitalChannel bottomIR;
    private DigitalChannel bottomIR2;
    private int countOfIntake;

    public void init (HardwareMap hardwareMap){
        rampMotor = hardwareMap.get(DcMotorEx.class, RAMP_NAME);
        rampMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rampState = RampState.IDLE;
        bottomIR = hardwareMap.get(DigitalChannel.class,BOTTOM_IR_NAME);
        bottomIR.setMode(DigitalChannel.Mode.INPUT);
        bottomIR2 = hardwareMap.get(DigitalChannel.class,BOTTOM_IR_2_NAME);
        bottomIR2.setMode(DigitalChannel.Mode.INPUT);

        targetPower = DEFAULT_TARGET_POWER;
    }
    public void setIdlePower(double power){ this.idlePower = power; }
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
    public void setLoading(){
        resetBallsLoaded();
        rampState = RampState.LOADING;
        countOfIntake = 0;
        doLoading();
    }
    public void setSlowRamp(){
        rampState = RampState.SLOW_RAMP;
        doSlowRamp();
    }
    public void setFeeding(){
        rampState = RampState.FEEDING;
        initialPosition = rampMotor.getCurrentPosition();
        doFeeding();
    }



    public void ballLoaded(){ballsLoaded++;}
    public void ballUnloaded(){ballsLoaded--;}
    public void resetBallsLoaded(){ ballsLoaded=0;}
    public int getBallsLoaded(){return ballsLoaded;}


    public boolean getBottomIRState(){
        return bottomIR.getState();
    }
    public boolean getUpperIRState(){
        return bottomIR2.getState();
    }
    public boolean isBallInIntake(){
        return !bottomIR.getState() || !bottomIR2.getState();
    }

    @Override
    public void loop(){
        if (rampState == RampState.IDLE) doIdle();
        else if (rampState == RampState.SLOW_RAMP) doSlowRamp();
        else if (rampState == RampState.LOADING) doLoading();
        else if (rampState == RampState.LOADING_RAISE_BALL) doLoadingRaiseBall();
        else if (rampState == RampState.FEEDING) doFeeding();
    }
    private void doIdle() {
        rampMotor.setPower(idlePower);
    }
    private void doLoading() {
        rampMotor.setPower(0.0);
        if (isBallInIntake()){
            if (countOfIntake > 1) {
                initialPosition = rampMotor.getCurrentPosition();
                ballLoaded();
                if (ballsLoaded <3)
                    rampState = RampState.LOADING_RAISE_BALL;
                else setIdleRamp();
            }else {
                countOfIntake ++;
            }
        }else {
            countOfIntake =0;
        }
    }
    private void doLoadingRaiseBall(){
        rampMotor.setPower(SLOW_RAMP_POWER);
        if (rampMotor.getCurrentPosition() > initialPosition+LOADING_DISTANCE){
            rampState = RampState.LOADING;
        }
    }
    private void doSlowRamp()   {
        rampMotor.setPower(SLOW_RAMP_POWER);
    }
    private void doFeeding() {
        if (rampMotor.getCurrentPosition() > initialPosition + FEED_DISTANCE){
            setIdleRamp();
            ballUnloaded();
        }
        rampMotor.setPower(targetPower);
    }


    public String getState(){
        return rampState.toString();
    }
    public enum RampState{
        IDLE,  LOADING, LOADING_RAISE_BALL,  SLOW_RAMP, FEEDING
    }

}







