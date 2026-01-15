package org.firstinspires.ftc.teamcode.commands.simple.drive;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class DriveStraightDistance extends DriveCommand {
    private Drivetrain drivetrain;
    private double power;
    private int distance;
    private Telemetry telemetry;
    private int currentEncoderReading;
    private boolean direction;  //either  true for forward, false for back
    public DriveStraightDistance(Drivetrain drivetrain, double power, int distance, Telemetry telemetry){
        this.drivetrain = drivetrain;
        //registerCommandSubsystem(drivetrain);
        this.distance = distance;
        this.telemetry = telemetry;
        this.power = power;
    }
    
    public void beginImpl(){
        currentEncoderReading = drivetrain.getEncoderReading();
        drivetrainController.driveRobotRelative(power,0,0);
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
        drivetrainController.setDriveToZero();
    }
    @Override
    public String writeName() {
        return "Drive Straight Distance";
    }
    // todo: write your code here
}