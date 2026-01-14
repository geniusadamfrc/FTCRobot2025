package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.prism.GoBildaPrismDriver;

public class LEDs extends Subsystem{
    private GoBildaPrismDriver leds;
    public static final String LED_NAME = "leds";
    private State state;

    public void init(HardwareMap hardwareMap){
        leds = hardwareMap.get(GoBildaPrismDriver.class, LED_NAME);
        state = State.Off;
        setOff();

    }

    public void setOff(){
        if (state != State.Off){
            leds.loadAnimationsFromArtboard(GoBildaPrismDriver.Artboard.ARTBOARD_1);
            state = State.Off;
        }
    }
    public void setNoLoaded(){

    }
    public void set1Loaded(){

    }
    public void set2Loaded(){

    }
    public void set3Loaded(){

    }
    public void setSpeedingUp(){

    }
    public void setReadyForShot(){

    }
    public void setInitial(){

    }
    public void setParked(){

    }

    public void loop(){
        if (Robot.robot.isIntaking()){
            int balls = Robot.ramp.getBallsLoaded();
            if (balls == 0) setNoLoaded();
            else if (balls== 1) set1Loaded();
            else if (balls ==2 ) set2Loaded();
            else if (balls ==3) set3Loaded();
        }
        else if (Robot.robot.isShooting()){
           if (Robot.robot.isReadyForShot()) setReadyForShot();
           else setSpeedingUp();

        }
        else {
            setOff();
        }
    }

    private enum State {
        NoLoaded, Loaded1, Loaded2, Loaded3, ReadyForShot, SpeedingUp, Off
    }
}
