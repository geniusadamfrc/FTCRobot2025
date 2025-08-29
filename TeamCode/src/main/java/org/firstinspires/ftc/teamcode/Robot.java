package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.CommandManager;
import org.firstinspires.ftc.teamcode.gamepad.GamePadExtended;
import org.firstinspires.ftc.teamcode.subsystem.Drivetrain;

public class Robot {
    //These are default.  Don't touch.
    public static GamePadExtended gamepadex1;
    public static GamePadExtended gamepadex2;
    //update these
    public static Drivetrain drivetrain;


    private static boolean initialized;
    public static void init(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2){
        //These are default.  Don't touch.
        if (initialized) return;
        Robot.gamepadex1 = new GamePadExtended(gamepad1);
        Robot.gamepadex2 = new GamePadExtended(gamepad2);
        CommandManager.initialize();
        //update these
        drivetrain = new Drivetrain();
        drivetrain.init(hardwareMap, telemetry);

        //leave this
        initialized =true;
    }

    public static void update(){
        //These are default.  Don't touch.
        gamepadex1.update();
        gamepadex2.update();
        CommandManager.update();

        //update these
        Robot.drivetrain.loop();

    }

}
