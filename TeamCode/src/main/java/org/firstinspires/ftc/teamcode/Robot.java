package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystem.Drivetrain;

public class Robot {
    public static Drivetrain drivetrain;

    private static boolean initialized;
    public static void init(HardwareMap hardwareMap, Telemetry telemetry){
        if (initialized) return;

        drivetrain = new Drivetrain();
        drivetrain.init(hardwareMap, telemetry);

        initialized =true;
    }


}
