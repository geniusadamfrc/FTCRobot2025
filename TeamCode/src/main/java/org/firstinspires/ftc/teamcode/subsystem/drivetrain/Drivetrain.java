package org.firstinspires.ftc.teamcode.subsystem.drivetrain;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.simple.drive.ManualDriveCommand;
import org.firstinspires.ftc.teamcode.subsystem.CommandSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.shooter.TagNotFoundException;

import com.qualcomm.robotcore.util.ElapsedTime;


public class Drivetrain extends CommandSubsystem {
    public static final String leftFrontName = "Front Left Motor";
    public static final String rightFrontName = "Front Right Motor";
    public static final String leftBackName = "Back Left Motor";
    public static final String rightBackName = "Back Right Motor";
    public DcMotorEx leftFrontDrive   = null;
    public DcMotorEx  rightFrontDrive  = null;
    public DcMotorEx  leftBackDrive   = null;
    public DcMotorEx  rightBackDrive  = null;

    public DrivetrainAligner aligner;
    private ManualDriveCommand driveCommand; //this is the command that happens if you set the drive function
    private boolean commanded;
    private double fls;
    private double frs;
    private double bls;
    private double brs;


    public void init(HardwareMap hardwareMap){
        initMotors(hardwareMap);
        aligner = new DrivetrainAlignerPID();
        commanded= false;
    }
    private void initMotors(HardwareMap hardwareMap){
        leftFrontDrive  = hardwareMap.get(DcMotorEx.class, leftFrontName); //0
        rightFrontDrive = hardwareMap.get(DcMotorEx.class, rightFrontName); //3
        leftBackDrive  = hardwareMap.get(DcMotorEx.class, leftBackName); //1
        rightBackDrive = hardwareMap.get(DcMotorEx.class, rightBackName); //2

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left and right sticks forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setDriveCommand(ManualDriveCommand command){
        this.driveCommand = command;
        command.begin();
    }




    public void setBrakeMode(){
        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void setCoastMode(){
        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void driveFieldRelative(double x, double y, double spin, double currentHeadingInRadians){
        double strafe = Math.cos(currentHeadingInRadians)*x + Math.sin(currentHeadingInRadians)*y;
        double forward = -Math.sin(currentHeadingInRadians)*x + Math.cos(currentHeadingInRadians)*y;;
        driveRobotRelative(forward, spin, strafe);
    }
    public void driveRobotRelative(double forward, double turn, double strafe){
        double fls = forward + turn + strafe;
        double frs = forward - turn - strafe;
        double bls = forward + turn - strafe;
        double brs = forward - turn + strafe;

        addPowersRaw(fls, frs, bls,brs);

    }
    public void addPowersRaw(double fls, double frs, double bls, double brs){
        this.fls += fls;
        this.frs += frs;
        this.bls += bls;
        this.brs += brs;
    }


    public void setPowersRaw(double fls, double frs, double bls, double brs){
        double maxSpeed = computeMaxSpeed(fls, frs, bls, brs);
        double minSpeed = computeMinSpeed(fls, frs, bls, brs);
        maxSpeed = Math.max(Math.abs(maxSpeed), Math.abs(minSpeed));

        if(maxSpeed>1){
            fls = fls/maxSpeed;
            frs = frs/maxSpeed;
            bls = bls/maxSpeed;
            brs /= maxSpeed;
        }


        leftFrontDrive.setPower(fls);
        rightFrontDrive.setPower(frs);
        leftBackDrive.setPower(bls);
        rightBackDrive.setPower(brs);

    }
    private double computeMaxSpeed(double fls, double frs, double bls, double brs){
        double maxSpeed;
        maxSpeed = Math.max(frs, fls);
        maxSpeed = Math.max(maxSpeed, bls);
        maxSpeed = Math.max(maxSpeed, brs);
        return maxSpeed;
    }
    private double computeMinSpeed(double fls, double frs, double bls, double brs){
        double minSpeed = Math.min(frs, fls);
        minSpeed = Math.min(minSpeed, bls);
        minSpeed = Math.min(minSpeed, brs);
        return minSpeed;
    }




    public boolean isAligned(){
        return aligner.isAligned();
    }
    public void setCommand(){
        commanded = true;
        aligner.running = false;
    }
    public void setAlign(){
        commanded =false;
        aligner.startAlign();

    }
    public void setDrive(){
        commanded = false;
        aligner.running = false;
    }

    //@Override
    public void loop2(Telemetry telemetry) {
        driveCommand.loop();
        aligner.updateTargetOdo();
        aligner.loop();
        if (!commanded){
            setPowersRaw(fls,frs,bls, brs);
            fls =0; frs =0; bls = 0; brs =0;
        }

        telemetry.addData("Drivetrain Status", commanded ? "Commanded" : (aligner.running? "Aligning" : "Idle"));
        telemetry.addData("Error" , aligner.lastError);
    }

}