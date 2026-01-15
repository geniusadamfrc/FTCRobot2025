package org.firstinspires.ftc.teamcode.commands.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.ParallelCommand;
import org.firstinspires.ftc.teamcode.commands.SequentialCommand;
import org.firstinspires.ftc.teamcode.commands.simple.DriveForwardAndIntake;
import org.firstinspires.ftc.teamcode.commands.simple.WaitMSeconds;
import org.firstinspires.ftc.teamcode.commands.simple.drive.DriveStraightPath;
import org.firstinspires.ftc.teamcode.commands.simple.drive.DriveStraightPedro;
import org.firstinspires.ftc.teamcode.commands.simple.drive.ManualRobotRelativeMecanumDrive;
import org.firstinspires.ftc.teamcode.commands.simple.Shoot3Balls;
import org.firstinspires.ftc.teamcode.commands.simple.drive.MoveToPointOnField;
import org.firstinspires.ftc.teamcode.commands.simple.shooting.Shoot;
import org.firstinspires.ftc.teamcode.commands.simple.shooting.StartUpShooter;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Autonomous(name="Blue Shoot 6 and Pickup 3")
public class BlueShoot6Pickup3 extends BaseAutoCommand {

    @Override
    public int getGoalID() {
        return 20;
    }
    @Override
    public double getDefaultAngle(){return -45;}
    @Override
    public Pose2D getInitialPose() {
        return new Pose2D(DistanceUnit.INCH, -59, 58, AngleUnit.DEGREES, -35);
    }
    @Override
    public Command getMainCommand(){
        MecanumDrive roadrunner = new MecanumDrive(hardwareMap, getInitialPose());
        SequentialCommand main = new SequentialCommand();
        ParallelCommand sq1 = new ParallelCommand();
        sq1.addCommand(new DriveStraightPedro(Robot.drivetrain, Robot.drivetrain.follower, -24, telemetry));
        sq1.addCommand(new SpeedUpForShooting(620));
        main.addCommand(sq1);
        main.addCommand(new Shoot());
        //main.addCommand(new Turn90DegreesPath(-115));
        //main.addCommand(new IdentifyPattern());
        //main.addCommand(new Turn90Degrees(0.4, 170, telemetry ));


        main.addCommand(new MoveToPointOnField(-12,25,179, roadrunner));
        main.addCommand(new DriveForwardAndIntake(-33, roadrunner));
        ParallelCommand sq2 = new ParallelCommand();
        sq2.addCommand(new MoveToPointOnField(-20, 32, -35, roadrunner));
        sq2.addCommand(new StartUpShooter(620));
        main.addCommand(sq2);
        main.addCommand(new WaitMSeconds(200));
        main.addCommand(new Shoot());
        main.addCommand(new MoveToPointOnField(-12, 0, 179, roadrunner));
        main.addCommand(new DriveForwardAndIntake(-30, roadrunner));

        ParallelCommand sq3 = new ParallelCommand();
        sq3.addCommand(new MoveToPointOnField(-20, 55, 0, roadrunner));
        sq3.addCommand(new StartUpShooter(620));
        main.addCommand(sq3);

        main.addCommand(new Shoot());

        //main.addCommand(new Turn90Degrees(Robot.drivetrain, 0.3, 90, telemetry));
        //main.addCommand(new DriveStraight(Robot.drivetrain, 0.0, 0.5, 300, telemetry));
        return main;
    }

}
