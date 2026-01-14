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
    private State state;
    private DigitalChannel bottomIR;
    private DigitalChannel bottomIR2;
    private int countOfIntake;

    public void init (HardwareMap hardwareMap){
        rampMotor = hardwareMap.get(DcMotorEx.class, RAMP_NAME);
        rampMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        state = State.IDLE;
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
        state = State.IDLE;
        doIdle();
    }
    public void setLoading(){
        resetBallsLoaded();
        state = State.LOADING;
        countOfIntake = 0;
        doLoading();
    }

    public void setFeeding(){
        if (state!=State.FEEDING) {
            state = State.FEEDING;
            initialPosition = rampMotor.getCurrentPosition();
            doFeeding();
        }
    }



    public void ballLoaded(){ballsLoaded++;}
    public void ballUnloaded(){ballsLoaded--;}
    public void resetBallsLoaded(){ ballsLoaded=0;}
    public int getBallsLoaded(){return ballsLoaded;}

    public boolean isBallInIntake(){
        return !bottomIR.getState() || !bottomIR2.getState();
    }

    @Override
    public void loop(){
        if (state == State.IDLE) doIdle();
        else if (state == State.LOADING) doLoading();
        else if (state == State.LOADING_RAISE_BALL) doLoadingRaiseBall();
        else if (state == State.FEEDING) doFeeding();
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
                    state = State.LOADING_RAISE_BALL;
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
            state = State.LOADING;
        }
    }
    private void doFeeding() {
        if (rampMotor.getCurrentPosition() > initialPosition + FEED_DISTANCE){
            setIdleRamp();
            ballUnloaded();
        }
        rampMotor.setPower(targetPower);
    }


    public String getState(){
        return state.toString();
    }

    public boolean isRampIdle() {
        return state == State.IDLE;
    }

    public enum State {
        IDLE,  LOADING, LOADING_RAISE_BALL,  FEEDING
    }

}







