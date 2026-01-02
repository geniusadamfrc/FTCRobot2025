package org.firstinspires.ftc.teamcode.commands.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.CommandManager;
import org.firstinspires.ftc.teamcode.commands.ParallelCommand;
import org.firstinspires.ftc.teamcode.commands.SequentialCommand;
import org.firstinspires.ftc.teamcode.commands.simple.DriveForwardAndIntake;
import org.firstinspires.ftc.teamcode.commands.simple.Shoot3Balls;
import org.firstinspires.ftc.teamcode.commands.simple.WaitMSeconds;
import org.firstinspires.ftc.teamcode.commands.simple.drive.AlignTargetOdo;
import org.firstinspires.ftc.teamcode.commands.simple.drive.DriveStraightPath;
import org.firstinspires.ftc.teamcode.commands.simple.drive.ManualRobotRelativeMecanumDrive;
import org.firstinspires.ftc.teamcode.commands.simple.drive.MoveToPointOnField;
import org.firstinspires.ftc.teamcode.commands.simple.shooter.SpeedUpForShooting;

@Autonomous(name="Red Main Auto")
public class RedAutoShoot3Get3 extends BaseAutoCommand {


    @Override
    public int getGoalID() {
        return 24;
    }

    @Override
    public Pose2D getInitialPose() {
        return new Pose2D(DistanceUnit.INCH,59, 58, AngleUnit.DEGREES,-145);
    }
    @Override
    public Command getMainCommand() {
        SequentialCommand main = new SequentialCommand();
        ParallelCommand sq1 = new ParallelCommand();
        sq1.addCommand(new DriveStraightPath(Robot.drivetrain, Robot.drivetrain.roadRunnerController, 24));
        sq1.addCommand(new SpeedUpForShooting(620));
        main.addCommand(sq1);
        main.addCommand(new Shoot3Balls().init(Robot.shooter, Robot.ramp));


        main.addCommand(new MoveToPointOnField(18,4,0));
        main.addCommand(new DriveForwardAndIntake(33));

        ParallelCommand sq2 = new ParallelCommand();
        sq2.addCommand(new MoveToPointOnField(20, 32, -145));
        sq2.addCommand(new SpeedUpForShooting(620));
        main.addCommand(sq2);
        main.addCommand(new WaitMSeconds(200));
        main.addCommand(new AlignTargetOdo(true));
        main.addCommand(new Shoot3Balls().init(Robot.shooter, Robot.ramp));
        main.addCommand(new MoveToPointOnField(20, -20, 0));
        main.addCommand(new DriveForwardAndIntake(33));

        return main;
    }
}
