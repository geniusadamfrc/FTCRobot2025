package org.firstinspires.ftc.teamcode.commands.simple.shooter;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;

public class ShooterIdle extends Command {

    public ShooterIdle(){
        registerSubsystem(Robot.shooter);
    }
    @Override
    public void beginImpl() {
        Robot.shooter.setIdleShooter();
    }

    @Override
    public void loopImpl() {
        finish();
    }
}
