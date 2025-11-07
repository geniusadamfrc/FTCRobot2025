package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.CommandManager;
import org.firstinspires.ftc.teamcode.gamepad.GamePadExtended;
import org.firstinspires.ftc.teamcode.subsystem.Ramp;
import org.firstinspires.ftc.teamcode.subsystem.Shooter;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain;

public class Robot {
    //These are default.  Don't touch.
    public static GamePadExtended gamepadex1;
    public static GamePadExtended gamepadex2;
    //update these
    public static Drivetrain drivetrain;
    public static Shooter shooter;
    public static Ramp ramp;

    public static void init(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2){
        //These are default.  Don't touch.
        if (gamepadex1 == null) gamepadex1 = new GamePadExtended(gamepad1);
        if (gamepadex2 == null) gamepadex2 = new GamePadExtended(gamepad2);

        //update these
        if (drivetrain == null) drivetrain = new Drivetrain();
        drivetrain.init(hardwareMap, telemetry);
        if (shooter == null) shooter = new Shooter();
        shooter.init(hardwareMap);
        if (ramp == null) ramp = new Ramp();
        ramp.init(hardwareMap);

    }

    public static void update(Telemetry telemetry){
        //These are default.  Don't touch.
        gamepadex1.update();
        gamepadex2.update();
        CommandManager.update();

        //update these
        Robot.drivetrain.loop();
        telemetry.addData("Drivetrain: ", drivetrain.getCurrentCommand());
        shooter.loop();
    }


}
