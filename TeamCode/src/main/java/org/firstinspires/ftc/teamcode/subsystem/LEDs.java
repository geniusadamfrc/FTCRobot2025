package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.prism.GoBildaPrismDriver;

public class LEDs extends Subsystem{
    private GoBildaPrismDriver leds;
    public static final String LED_NAME = "leds";


    public void init(HardwareMap hardwareMap){
        leds = hardwareMap.get(GoBildaPrismDriver.class, LED_NAME);
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
}
