package org.firstinspires.ftc.teamcode.commands.simple;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystem.Drivetrain;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.*;

public class Turn90Degrees extends Command {
    private Drivetrain drivetrain;
    //private IMUSub imu;
    private double power;
    private int angle;
    private Telemetry telemetry;
    private double targetAngle;
    private boolean direction;  //either  true for forward, false for back
    public Turn90Degrees(Drivetrain drivetrain,  double power, int angle, Telemetry telemetry){
        this.drivetrain = drivetrain;
        //this.imu = imu;
        registerSubsystem(drivetrain);
        this.angle = angle;
        this.telemetry = telemetry;
        this.power = power;
    }
    
    public void beginImpl(){
        //imu.resetYaw();
        drivetrain.drive(0,power,0);
        telemetry.addData("Turn 90", "begin");
        direction = false;
        if (power < 0) direction = true; 
    }
    public void loopImpl(){
        telemetry.addData("Turn 90", "loop");
        //telemetry.addData("Current Angle", (imu.getHeading()) + "");
        /*if ((direction && angle < imu.getHeading())
            ||
            (!direction && angle > imu.getHeading())
            ){
            drivetrain.drive(0,0,0);
            finish();
        }
        */

    }
    // todo: write your code here
}