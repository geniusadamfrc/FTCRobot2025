package org.firstinspires.ftc.teamcode.commands.simple;

import org.firstinspires.ftc.teamcode.commands.SequentialCommand;
import org.firstinspires.ftc.teamcode.subsystem.Ramp;
import org.firstinspires.ftc.teamcode.subsystem.Shooter;
import org.firstinspires.ftc.teamcode.commands.simple.shooter.*;

public class Shoot3Balls extends SequentialCommand {


    public Shoot3Balls init(Shooter shooter, Ramp ramp){
        super.addCommand(new ShootBall());
        super.addCommand(new ShootBall());
        super.addCommand(new ShootBall());
        super.addCommand(new ShooterIdle());
        return this;
    }
}
