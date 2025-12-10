package org.firstinspires.ftc.teamcode.commands.simple.drive;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Turn90Degrees extends Command {
    private double power;
    private int angle;
    private Telemetry telemetry;
    private double targetAngle;
    private boolean direction;  //either  true for forward, false for back
    public Turn90Degrees( double power, int angle, Telemetry telemetry){
        //this.imu = imu;
        registerSubsystem(Robot.drivetrain);
        this.angle = angle;
        this.telemetry = telemetry;
        this.power = power;
    }
    
    public void beginImpl(){
        //imu.resetYaw();
        Robot.drivetrain.driveRobotRelative(0,power,0);
        telemetry.addData("Turn 90", "begin");
        direction = false;
        if (power < 0) direction = true; 
    }
    public void loopImpl(){
        telemetry.addData("Turn 90", "loop");
        telemetry.addData("Current Angle", (Robot.drivetrain.getHeading()) + "");
        if ((direction && angle < Robot.drivetrain.getHeading())
            ||
            (!direction && angle > Robot.drivetrain.getHeading())
            ){
            Robot.drivetrain.setDriveToZero();
            finish();
        }
    }
    // todo: write your code here
}