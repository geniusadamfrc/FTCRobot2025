package org.firstinspires.ftc.teamcode.subsystem.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.simple.drive.DriveCommand;
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
    private DrivetrainController currentController;
    private ManualDriveCommand driveCommand; //this is the command that happens if you set the drive function

    public void init(HardwareMap hardwareMap){
        initMotors(hardwareMap);
        aligner = new DrivetrainAligner();
        aligner.controller = new DrivetrainController();
        aligner.controller.setControllable(aligner);
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
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setDriveCommand(ManualDriveCommand command){
        this.driveCommand = command;
        DrivetrainController controller = new DrivetrainController();
        command.setDrivetrainController(controller);
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
    private void driveRobotRelative(double forward, double turn, double strafe){
        double fls = forward + turn + strafe;
        double frs = forward - turn - strafe;
        double bls = forward + turn - strafe;
        double brs = forward - turn + strafe;
         
        double maxSpeed = computeMaxSpeed(fls, frs, bls, brs);
        double minSpeed = computeMinSpeed(fls, frs, bls, brs);
        maxSpeed = Math.max(Math.abs(maxSpeed), Math.abs(minSpeed));
        
        if(maxSpeed>1){
            fls = fls/maxSpeed;
            frs = frs/maxSpeed;
            bls = bls/maxSpeed;
            brs /= maxSpeed;
        }
        setPowersRaw(fls, frs, bls,brs);
        
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

    private void setPowersRaw(double fls, double frs, double bls, double brs){
        leftFrontDrive.setPower(fls);
        rightFrontDrive.setPower(frs);
        leftBackDrive.setPower(bls);
        rightBackDrive.setPower(brs);

    }
    public int getEncoderReading(){
        return (leftFrontDrive.getCurrentPosition()
            + leftBackDrive.getCurrentPosition()
            + rightFrontDrive.getCurrentPosition()
            + rightBackDrive.getCurrentPosition())/4;
    }


    public boolean isAligned(){
        return aligner.isAligned();
    }
    public DrivetrainController setCommand(){
        aligner.running = false;
        DrivetrainController controller = new DrivetrainController();
        currentController = controller;
        return controller;
    }
    public void setAlign(){
        currentController = aligner.controller;
        aligner.startAlign();

    }
    public void setDrive(){
        aligner.running = false;
        currentController = driveCommand.getDrivetrainController();
    }

    //@Override
    public void loop2(Telemetry telemetry) {
        driveCommand.loop();
        aligner.loop();
        currentController.drive(this);
        telemetry.addData("Drivetrain Status:", currentController.getControllableName());
    }




    public static class DrivetrainAligner implements DrivetrainController.DrivetrainControllable {

        private double Kp = 0.03;
        private double Ki = 0.03;
        private double Kd = 0.00;
        private double integralSum = 0;
        private double targetOdo;
        private double lastError = 0;
        ElapsedTime timer;
        private double staticFeedForward = 0.05;
        private DrivetrainController controller;
        public double defaultAngle;
        public boolean running;
        public void setDefaultAngle(double defaultAngle) {
            this.targetOdo = defaultAngle;
        }

        public void startAlign(){
            running = true;
            updateTargetOdo();
            timer = new ElapsedTime();
        }

        public void updateTargetOdo(){
            try {
                this.targetOdo = Robot.odometry.getHeading() + Robot.shooter.camera.getBearing();
            } catch (TagNotFoundException e) {
            }
        }


        public void loop() {
            if (!running) return;
            updateTargetOdo();
            double error = Robot.odometry.getHeading() - targetOdo;
            // rate of change of the error
            double derivative = (error - lastError) / timer.seconds();
            // sum of all error over time
            if (error*lastError < 0) integralSum = 0;
            integralSum = integralSum + (error * timer.seconds());

            double out = (Kp * error) + (Ki * integralSum) + (Kd * derivative);
            double feedForward = staticFeedForward;
            if (isAligned() ) feedForward =0;
            out = out + (out < 0 ? -feedForward : feedForward);
            controller.driveRobotRelative(0.0, out, 0.0);
            lastError = error;
            // reset the timer for next time
            timer.reset();
        }
        public boolean isAligned(){
            double error = Robot.odometry.getHeading() - targetOdo;
            return Math.abs(error) < 1.5;
        }

        public double getTargetOdo(){
            return targetOdo;
        }
        public double getLastError(){
            return lastError;
        }

        @Override
        public String writeName() {
            return "Aligner";
        }
    }

    public static class DrivetrainController {
        private double forward;
        private double turn;
        private double strafe;
        private boolean ftsMode;

        private double fls;
        private double frs;
        private double bls;
        private double brs;
        private DrivetrainControllable controllable;


        public void driveRobotRelative(double forward, double turn, double strafe){
            ftsMode =true;
            this.forward = forward;
            this.turn = turn;
            this.strafe = strafe;
        }
        public void driveFieldRelative(double x, double y, double spin, double currentHeadingInRadians){
            double strafe = Math.cos(currentHeadingInRadians)*x + Math.sin(currentHeadingInRadians)*y;
            double forward = Math.sin(currentHeadingInRadians)*x + Math.cos(currentHeadingInRadians)*y;;
            driveRobotRelative(forward, spin, strafe);
        }


        public void setDriveToZero(){
            driveRobotRelative(0,0,0);
        }

        public void setPowersRaw(double fls, double frs, double bls, double brs){
            ftsMode = false;
            this.fls= fls;
            this.frs = frs;
            this.bls =bls;
            this.brs = brs;
        }

        public void drive(Drivetrain drivetrain){
            if (ftsMode) drivetrain.driveRobotRelative(forward, turn, strafe);
            else drivetrain.setPowersRaw(fls,frs,bls, brs);
        }
        public String getControllableName(){
            return controllable !=null ? controllable.writeName():"";
        }

        public interface DrivetrainControllable{
            public String writeName();
        }
        public void setControllable(DrivetrainControllable controllable){
            this.controllable = controllable;
        }
    }
}