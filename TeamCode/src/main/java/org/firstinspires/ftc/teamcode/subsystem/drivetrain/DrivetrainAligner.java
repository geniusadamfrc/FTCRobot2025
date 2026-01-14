package org.firstinspires.ftc.teamcode.subsystem.drivetrain;

import org.firstinspires.ftc.teamcode.subsystem.Subsystem;
import org.firstinspires.ftc.teamcode.subsystem.shooter.CameraSystem;

public class DrivetrainAligner extends Subsystem {
    private Drivetrain drivetrain;
    private Odometry odometry;
    private CameraSystem camera;
    public void init(Drivetrain drivetrain, Odometry odometry, CameraSystem camera){
        this.drivetrain = drivetrain;
        this.odometry = odometry;
        this.camera = camera;
    }

    public void setAlign(){
    }
    public void exitAlign(){

    }

}
