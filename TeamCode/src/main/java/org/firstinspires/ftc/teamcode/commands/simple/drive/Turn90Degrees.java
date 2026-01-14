package org.firstinspires.ftc.teamcode.commands.simple.drive;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Turn90Degrees extends DriveCommand {
    private double power;
    private int angle;
    private Telemetry telemetry;
    private double targetAngle;
    private boolean direction;  //either  true for forward, false for back
    public Turn90Degrees( double power, int angle, Telemetry telemetry){
        //this.imu = imu;
        registerCommandSubsystem(Robot.drivetrain);
        this.angle = angle;
        this.telemetry = telemetry;
        this.power = power;
    }
    
    public void beginImpl(){
        //imu.resetYaw();
        drivetrainController.driveRobotRelative(0,power,0);
        telemetry.addData("Turn 90", "begin");
        direction = false;
        if (power < 0) direction = true; 
    }
    public void loopImpl(){
        telemetry.addData("Turn 90", "loop");
        telemetry.addData("Current Angle", (Robot.odometry.getHeading()) + "");
        if ((direction && angle < Robot.odometry.getHeading())
            ||
            (!direction && angle > Robot.odometry.getHeading())
            ){
            drivetrainController.setDriveToZero();
            finish();
        }
    }
    // todo: write your code here
}