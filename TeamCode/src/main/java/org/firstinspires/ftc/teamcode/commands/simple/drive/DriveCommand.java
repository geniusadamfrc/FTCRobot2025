package org.firstinspires.ftc.teamcode.commands.simple.drive;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain;

public abstract class DriveCommand extends Command {
    protected Drivetrain.DrivetrainController drivetrainController;
    @Override
    public void begin(){
        drivetrainController = Robot.drivetrain.setCommand();
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
