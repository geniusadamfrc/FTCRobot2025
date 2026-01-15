package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.prism.GoBildaPrismDriver;
import java.util.HashMap;

public class LEDs extends Subsystem{
    private GoBildaPrismDriver leds;
    public static final String LED_NAME = "leds";
    private State state;

    private HashMap<State, GoBildaPrismDriver.Artboard> stateToArtboard;


    private boolean working;
    public void init(HardwareMap hardwareMap){
        working  =true;
        try {
            leds = hardwareMap.get(GoBildaPrismDriver.class, LED_NAME);
        }catch (Exception ex){
            working = false; return;
        }
        loadStates();
        state = State.Loaded1;
        setState(State.Off);
    }
    private void loadStates(){
        stateToArtboard = new HashMap<State, GoBildaPrismDriver.Artboard>();
        stateToArtboard.put(State.Off, GoBildaPrismDriver.Artboard.ARTBOARD_0);
        stateToArtboard.put(State.Loaded1, GoBildaPrismDriver.Artboard.ARTBOARD_1);
        stateToArtboard.put(State.Loaded2, GoBildaPrismDriver.Artboard.ARTBOARD_2);
        stateToArtboard.put(State.Loaded3, GoBildaPrismDriver.Artboard.ARTBOARD_3);
        stateToArtboard.put(State.NoLoaded, GoBildaPrismDriver.Artboard.ARTBOARD_4);
        stateToArtboard.put(State.SpeedingUp, GoBildaPrismDriver.Artboard.ARTBOARD_5);
        stateToArtboard.put(State.ReadyForShot, GoBildaPrismDriver.Artboard.ARTBOARD_6);
        stateToArtboard.put(State.Parked, GoBildaPrismDriver.Artboard.ARTBOARD_7);


    }



    private void setState(State newState){
        if (newState == state) return;
        leds.loadAnimationsFromArtboard(stateToArtboard.get(newState));
        state = newState;
    }

    public void loop(){
        if (!working) return;
        if (Robot.robot.isIntaking()){
            int balls = Robot.ramp.getBallsLoaded();
            if (balls == 0) setState(State.NoLoaded);
            else if (balls== 1) setState(State.Loaded1);
            else if (balls ==2 ) setState(State.Loaded2);
            else if (balls ==3) setState(State.Loaded3);
        }
        else if (Robot.robot.isShooting()){
           if (Robot.robot.isReadyForShot()) setState(State.ReadyForShot);
           else setState(State.SpeedingUp);

        }
        else {
            setState(State.Off);
        }
    }

    private enum State {
        NoLoaded, Loaded1, Loaded2, Loaded3, ReadyForShot, SpeedingUp, Off, Parked
    }
}
