package org.firstinspires.ftc.teamcode.commands.simple.drive;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.gamepad.Axis;

public class ManualRobotRelativeMecanumDrive extends ManualDriveCommand {
    private Axis left_stick_y;
    private Axis left_stick_x;
    private Axis right_stick_x;

    public ManualRobotRelativeMecanumDrive(Axis left_stick_y, Axis left_stick_x, Axis right_stick_x){
        this.left_stick_y = left_stick_y;
        this.left_stick_x = left_stick_x;
        this.right_stick_x = right_stick_x;
        registerCommandSubsystem(Robot.drivetrain);
    }

    @Override
    public void beginImpl() {

    }

    @Override
    public void loopImpl() {
        double forward = -left_stick_y.getState();
        double turn = right_stick_x.getState();
        double strafe = left_stick_x.getState();
        Robot.drivetrain.driveRobotRelative(forward, turn, strafe);
    }



}
