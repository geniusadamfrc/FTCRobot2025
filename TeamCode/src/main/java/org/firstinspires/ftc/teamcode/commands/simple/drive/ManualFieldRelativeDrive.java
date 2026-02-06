package org.firstinspires.ftc.teamcode.commands.simple.drive;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.gamepad.Axis;

public class ManualFieldRelativeDrive extends ManualDriveCommand {
    private final Axis left_stick_y;
    private final Axis left_stick_x;
    private final Axis right_stick_x;

    public ManualFieldRelativeDrive(Axis left_stick_y, Axis left_stick_x, Axis right_stick_x, boolean blue_side){
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
        double currentHeading = Robot.odometry.odo.getHeading(AngleUnit.RADIANS);
        Robot.drivetrain.driveFieldRelative(forward, strafe, turn, currentHeading);
    }



}
