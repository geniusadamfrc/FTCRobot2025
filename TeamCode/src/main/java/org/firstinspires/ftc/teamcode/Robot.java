package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.CommandManager;
import org.firstinspires.ftc.teamcode.subsystem.Drivetrain;

public class Robot {
    //These are default.  Don't touch.

    //update these
    public static Drivetrain drivetrain;


    private static boolean initialized;
    public static void init(HardwareMap hardwareMap, Telemetry telemetry){
        //These are default.  Don't touch.
        if (initialized) return;
        CommandManager.initialize();
        //update these
        drivetrain = new Drivetrain();
        drivetrain.init(hardwareMap, telemetry);

        //leave this
        initialized =true;
    }

    public static void update(){
        //These are default.  Don't touch.
        CommandManager.update();

        //update these
        Robot.drivetrain.loop();

    }

}
