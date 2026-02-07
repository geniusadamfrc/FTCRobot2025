package org.firstinspires.ftc.teamcode.commands.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.ParallelCommand;
import org.firstinspires.ftc.teamcode.commands.SequentialCommand;
import org.firstinspires.ftc.teamcode.commands.simple.DriveForwardAndIntake;
import org.firstinspires.ftc.teamcode.commands.simple.Intake;
import org.firstinspires.ftc.teamcode.commands.simple.WaitMSeconds;
import org.firstinspires.ftc.teamcode.commands.simple.drive.MoveToPointOnField;
import org.firstinspires.ftc.teamcode.commands.simple.shooting.Shoot;
import org.firstinspires.ftc.teamcode.commands.simple.shooting.StartUpShooter;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Autonomous(name="Red Back Auto")
public class RedBackAuto extends BaseAutoCommand {

    @Override
    public int getGoalID() {
        return 24;
    }
    @Override
    public double getDefaultAngle(){return -110;}
    @Override
    public Pose2D getInitialPose() {
        return new Pose2D(DistanceUnit.INCH, 9, -54, AngleUnit.DEGREES, -90);
    }
    @Override
    public Command getMainCommand(){
        MecanumDrive roadrunner = new MecanumDrive(hardwareMap, getInitialPose());
        SequentialCommand main = new SequentialCommand();
        ParallelCommand sq1 = new ParallelCommand();
        main.addCommand(new MoveToPointOnField(9, -48, -110, roadrunner));
        sq1.addCommand(new StartUpShooter(620));
        main.addCommand(sq1);
        main.addCommand(new Shoot());
        main.addCommand(new Intake());


        main.addCommand(new MoveToPointOnField(24,-36,0, roadrunner));
        main.addCommand(new DriveForwardAndIntake(30, 0, roadrunner));
        ParallelCommand sq2 = new ParallelCommand();
        sq2.addCommand(new MoveToPointOnField(14, -48, -110, roadrunner));
        sq2.addCommand(new StartUpShooter(620));
        main.addCommand(sq2);
        //main.addCommand(new WaitMSeconds(200));
        main.addCommand(new Shoot());
        main.addCommand(new Intake());



        main.addCommand(new MoveToPointOnField(30,-20,0, roadrunner));
        main.addCommand(new DriveForwardAndIntake(24, 0, roadrunner));
        ParallelCommand sq3 = new ParallelCommand();
        sq3.addCommand(new MoveToPointOnField(14, -48, -110, roadrunner));
        sq3.addCommand(new StartUpShooter(620));
        main.addCommand(sq3);
        main.addCommand(new Shoot());
        main.addCommand(new MoveToPointOnField(12,-36,0, roadrunner));

        return main;

    }

}
