package org.firstinspires.ftc.teamcode.commands.simple.drive;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain.DrivetrainController.DrivetrainControllable;

public abstract class ManualDriveCommand extends Command implements DrivetrainControllable {
    protected Drivetrain.DrivetrainController drivetrainController;

    public Drivetrain.DrivetrainController getDrivetrainController() {
        return drivetrainController;
    }

    public void setDrivetrainController(Drivetrain.DrivetrainController drivetrainController) {
        this.drivetrainController = drivetrainController;
        drivetrainController.setControllable(this);
    }
}
