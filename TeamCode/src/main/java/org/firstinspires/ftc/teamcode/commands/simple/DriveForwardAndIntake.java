package org.firstinspires.ftc.teamcode.commands.simple;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.simple.drive.MoveToPointOnField;
import org.firstinspires.ftc.teamcode.commands.simple.drive.MoveToPointOnFieldSlow;
import org.firstinspires.ftc.teamcode.commands.simple.ramp.PushBallUp;
import org.firstinspires.ftc.teamcode.commands.simple.ramp.SlowRampUp;
import org.firstinspires.ftc.teamcode.commands.simple.ramp.TurnOnIntake;

public class DriveForwardAndIntake extends Command {
    private TurnOnIntake intake;
    private MoveToPointOnFieldSlow drive;
    private double heading;
    //private SlowRampUp rampUp;
    private double distance;
    public DriveForwardAndIntake(double distance, double heading){
        this.distance = distance;
        this.heading = heading;
    }
    @Override
    public void beginImpl() {
        double currentY = Robot.drivetrain.getOdoPosition().getY(DistanceUnit.INCH);
        double currentX = Robot.drivetrain.getOdoPosition().getX(DistanceUnit.INCH);
        //double currentHeading = Robot.drivetrain.getHeading();
        drive = new MoveToPointOnFieldSlow(currentX+ distance, currentY, heading);
        intake = new TurnOnIntake(-1.0, -0.25);
        //rampUp = new SlowRampUp();
        drive.begin();
        intake.begin();

    }

    @Override
    public void loopImpl() {
        intake.loop();
        drive.loop();
      //  rampUp.loop();
        if (drive.isFinished()){
            intake.finish();
        //    rampUp.finish();
            finish();
        }
    }
}
