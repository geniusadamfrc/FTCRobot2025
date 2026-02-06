package org.firstinspires.ftc.teamcode.commands.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.PoseConverter;
import com.pedropathing.geometry.PedroCoordinates;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.ParallelCommand;
import org.firstinspires.ftc.teamcode.commands.SequentialCommand;
import org.firstinspires.ftc.teamcode.commands.simple.DriveForwardAndIntake;
import org.firstinspires.ftc.teamcode.commands.simple.Intake;
import org.firstinspires.ftc.teamcode.commands.simple.WaitMSeconds;
import org.firstinspires.ftc.teamcode.commands.simple.drive.DriveStraightPedro;
import org.firstinspires.ftc.teamcode.commands.simple.drive.MoveToPointOnFieldPedro;
import org.firstinspires.ftc.teamcode.commands.simple.shooting.Shoot;
import org.firstinspires.ftc.teamcode.commands.simple.shooting.StartUpShooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Autonomous(name="Blue Shoot 6 Pedro")
public class BlueShoot6Pickup3Pedro extends BaseAutoCommand {
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
        //MecanumDrive roadrunner = new MecanumDrive(hardwareMap, getInitialPose());
        Follower follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(PoseConverter.pose2DToPose(getInitialPose(), PedroCoordinates.INSTANCE));
        SequentialCommand main = new SequentialCommand();
        ParallelCommand sq1 = new ParallelCommand();
        sq1.addCommand(new DriveStraightPedro(Robot.drivetrain, follower, -36, telemetry));
        sq1.addCommand(new StartUpShooter(620));
        main.addCommand(sq1);
        main.addCommand(new WaitMSeconds(200));
        main.addCommand(new Shoot());
        main.addCommand(new Intake());

        main.addCommand(new MoveToPointOnFieldPedro(-12,25,179, follower));
        //main.addCommand(new DriveForwardAndIntake(-33,  roadrunner));
        ParallelCommand sq2 = new ParallelCommand();
        sq2.addCommand(new MoveToPointOnFieldPedro(-20, 32, -35, follower));
        sq2.addCommand(new StartUpShooter(620));
        main.addCommand(sq2);
        main.addCommand(new WaitMSeconds(200));
        main.addCommand(new Shoot());
        main.addCommand(new Intake());
        main.addCommand(new MoveToPointOnFieldPedro(-12, 0, 179, follower));
        //main.addCommand(new DriveForwardAndIntake(-30,  roadrunner));
        /*
        ParallelCommand sq3 = new ParallelCommand();
        sq3.addCommand(new MoveToPointOnField(-20, 32, -35));
        sq3.addCommand(new SpeedUpForShooting(620));
        main.addCommand(sq3);
        main.addCommand(new WaitMSeconds(200));
        main.addCommand(new AlignTargetOdo(true));
        main.addCommand(new Shoot3Balls().init(Robot.shooter, Robot.ramp));
        */
        //main.addCommand(new Turn90Degrees(Robot.drivetrain, 0.3, 90, telemetry));
        //main.addCommand(new DriveStraight(Robot.drivetrain, 0.0, 0.5, 300, telemetry));
        return main;
    }
}
