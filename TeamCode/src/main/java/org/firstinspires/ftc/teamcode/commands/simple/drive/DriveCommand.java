package org.firstinspires.ftc.teamcode.commands.simple.drive;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain;

public abstract class DriveCommand extends Command implements Drivetrain.DrivetrainController.DrivetrainControllable {
    protected Drivetrain.DrivetrainController drivetrainController;
    @Override
    public void begin(){
        drivetrainController = Robot.drivetrain.setCommand();
        drivetrainController.setControllable(this);
        super.begin();
    }
    @Override
    public void finish(){
        Robot.drivetrain.setDrive();
        super.finish();
    }
    public Drivetrain.DrivetrainController getDrivetrainController(){
        return drivetrainController;
    }
}
