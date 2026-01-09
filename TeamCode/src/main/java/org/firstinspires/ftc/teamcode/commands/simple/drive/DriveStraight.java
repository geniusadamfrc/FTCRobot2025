package org.firstinspires.ftc.teamcode.commands.simple.drive;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.TimerCommand;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class DriveStraight extends Command {
    private Drivetrain drivetrain;
    private TimerCommand timer;
    private int time;
    private double forward;
    private double strafe;
    private Telemetry telemetry;
    public DriveStraight(Drivetrain drivetrain, double forward, double strafe, int timeInMilliseconds, Telemetry telemetry){
        this.drivetrain = drivetrain;
        registerCommandSubsystem(drivetrain);
        this.forward = forward;
        this.strafe = strafe;
        this.time = timeInMilliseconds;
        this.telemetry = telemetry;
    }
    
    public void beginImpl(){
        timer = new TimerCommand(time);
        timer.begin();
        drivetrain.driveRobotRelative(forward,0,strafe);
        telemetry.addData("DriveStraight Begin", "true");
    }
    public void loopImpl(){
        telemetry.addData("DriveStraight Loop", "true");
        if (timer.isFinished()){
            drivetrain.setDriveToZero();
            finish();
        }else {
            timer.loop();    
        }
        
    }
    public void finishImpl(){
        drivetrain.setDriveToZero();
    }
    // todo: write your code here
}