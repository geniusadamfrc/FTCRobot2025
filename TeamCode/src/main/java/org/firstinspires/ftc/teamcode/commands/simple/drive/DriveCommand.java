package org.firstinspires.ftc.teamcode.commands.simple.drive;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;

public abstract class DriveCommand extends Command {

    @Override
    public void begin(){
        Robot.drivetrain.setCommand(this);
        super.begin();
    }
    @Override
    public void finish(){
        Robot.drivetrain.setDrive();
        super.finish();
    }
}
