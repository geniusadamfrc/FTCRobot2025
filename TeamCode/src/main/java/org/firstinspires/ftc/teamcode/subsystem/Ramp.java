package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Ramp extends Subsystem{
    public static final String RAMP_NAME = "ramp";
    public static final String BOTTOM_IR_NAME = "bottomIRSensor";
    public static final String BOTTOM_IR_2_NAME = "upperIRSensor";
    public static final double DEFAULT_TARGET_POWER = 1.0;
    public static final double SLOW_RAMP_POWER = 1.0;
    private static final double LAST_LOADING_DISTANCE = 100;
    private static final double LOADING_DISTANCE = 300;
    private static final double FEED_DISTANCE = 600 ;
    private static final float GAIN = 100;
    private DcMotorEx rampMotor;

    private double targetPower;
    private double idlePower;
    private int ballsLoaded;
    private double initialPosition;
    private State state;
    private DigitalChannel bottomIR;
    private DigitalChannel bottomIR2;
    private ColorSensor colorSensor;
    private DistanceSensor colorSensorDistance;
    private int countOfIntake;
    private boolean manualIndexing;
    private boolean manualIndexLoadBall;

    public void init (HardwareMap hardwareMap){
        rampMotor = hardwareMap.get(DcMotorEx.class, RAMP_NAME);
        rampMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        state = State.IDLE;
        bottomIR = hardwareMap.get(DigitalChannel.class,BOTTOM_IR_NAME);
        bottomIR.setMode(DigitalChannel.Mode.INPUT);
        bottomIR2 = hardwareMap.get(DigitalChannel.class,BOTTOM_IR_2_NAME);
        bottomIR2.setMode(DigitalChannel.Mode.INPUT);
        colorSensor = hardwareMap.get(ColorSensor.class, "color_sensor");
        colorSensorDistance = hardwareMap.get(DistanceSensor.class, "color_sensor");
        ((NormalizedColorSensor) colorSensor).setGain(GAIN);
        targetPower = DEFAULT_TARGET_POWER;
        manualIndexing = false;
    }
    public void setIdlePower(double power){ this.idlePower = power; }
    public double getRampPosition() {
        return rampMotor.getCurrentPosition();
    }

    public void setIdleRamp(){
        state = State.IDLE;
        doIdle();
        colorSensor.enableLed(false);
    }
    public void setLoading(){
        resetBallsLoaded();
        state = State.LOADING;
        countOfIntake = 0;
        doLoading();
        colorSensor.enableLed(true);
    }
    public void setLastLoad(){
        initialPosition = rampMotor.getCurrentPosition();
        state = State.LAST_LOADING;

    }
    public void setFeeding(){
        if (state!=State.FEEDING) {
            state = State.FEEDING;
            initialPosition = rampMotor.getCurrentPosition();
            doFeeding();
        }
    }


    public void setManualIndexing(boolean manualIndexing){
        this.manualIndexing = manualIndexing;
    }
    public void setManualIndexLoadBall(boolean manualIndexLoadBall){
        this.manualIndexLoadBall = manualIndexLoadBall;
    }

    public void ballLoaded(){ballsLoaded++;}
    public void ballUnloaded(){ballsLoaded--;}
    public void resetBallsLoaded(){ ballsLoaded=0;}
    public int getBallsLoaded(){return ballsLoaded;}
    public void setBallsLoaded(int ballsLoaded) {
        this.ballsLoaded = ballsLoaded;
    }

    public boolean isBallInIntake()
    {
        if (manualIndexLoadBall) return true;
        if (manualIndexing) return false;

        NormalizedRGBA myNormalizedColors = ((NormalizedColorSensor) colorSensor).getNormalizedColors();
        // Convert the normalized color values to an Android color value.
        int myColor = myNormalizedColors.toColor();
        //purple:  0.17, 0.2, 0.3 -- 0.2, 0.25, 0.38 -- 0.235, 0.278, 0.42  // in a hole 0.1, 0.13, 0.174
        //Distance 6.6 - 2.6
        //unloaded distance 6.8 low
        //unloaded 0.07, 0.12, 0.08
        //green:  0.1, 0.37, 0.27 - 0.08, 0.29, 0.22 -- 0.11, 0.39, 0.29  // on a hole 0.007, 0.25, 0.18
        //Distance:  6

        double red = myNormalizedColors.red;
        double green  = myNormalizedColors.green;
        double blue = myNormalizedColors.blue;
        double distance = colorSensorDistance.getDistance(DistanceUnit.CM);

        return green > 0.2 || blue > 0.15 || distance < 4;

    }

    @Override
    public void loop(){
        if (state == State.IDLE) doIdle();
        else if (state == State.LOADING) doLoading();
        else if (state == State.LOADING_RAISE_BALL) doLoadingRaiseBall();
        else if (state == State.FEEDING) doFeeding();
        else if (state == State.LAST_LOADING)doLastLoading();
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
    private void doLastLoading(){
        rampMotor.setPower(SLOW_RAMP_POWER);
        if (rampMotor.getCurrentPosition() > initialPosition+LAST_LOADING_DISTANCE){
            setIdleRamp();
        }
    }

    private void doFeeding() {
        rampMotor.setPower(targetPower);
        if (rampMotor.getCurrentPosition() > initialPosition + FEED_DISTANCE){
            setIdleRamp();
            ballUnloaded();
        }

    }


    public String getState(){
        return state.toString();
    }

    public void writeOut(Telemetry telemetry){
        telemetry.addData("Ramp: ", state.toString());
        telemetry.addData("Ramp Position:", rampMotor.getCurrentPosition());
    }
    public boolean isRampIdle() {
        return state == State.IDLE;
    }

    private enum State {
        IDLE,  LOADING, LOADING_RAISE_BALL, LAST_LOADING,  FEEDING
    }

}







