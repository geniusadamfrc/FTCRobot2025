package org.firstinspires.ftc.teamcode.commands.simple;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystem.Drivetrain;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class DriveStraightDistance extends Command {
    private Drivetrain drivetrain;
    private double power;
    private int distance;
    private Telemetry telemetry;
    private int currentEncoderReading;
    private boolean direction;  //either  true for forward, false for back
    public DriveStraightDistance(Drivetrain drivetrain, double power, int distance, Telemetry telemetry){
        this.drivetrain = drivetrain;
        registerSubsystem(drivetrain);
        this.distance = distance;
        this.telemetry = telemetry;
        this.power = power;
    }
    
    public void beginImpl(){
        currentEncoderReading = drivetrain.getEncoderReading();
        drivetrain.drive(power,0,0);
        telemetry.addData("DriveStraightDistance", "begin");
        direction = false;
        if (power > 0) direction = true; 
    }
    public void loopImpl(){
        telemetry.addData("DriveStraightDistance", "loop");
        telemetry.addData("Encoder", (drivetrain.getEncoderReading()) + "");
        if ((direction && currentEncoderReading + distance < drivetrain.getEncoderReading())
            ||
            (!direction && currentEncoderReading + distance > drivetrain.getEncoderReading())
            ){
            finish();
        }
    }
    public void finishImpl(){
        drivetrain.drive(0,0,0);
    }
    // todo: write your code here
}