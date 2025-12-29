package org.firstinspires.ftc.teamcode.subsystem.drivetrain;

import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.PoseConverter;
import com.pedropathing.geometry.CoordinateSystem;
import com.pedropathing.geometry.PedroCoordinates;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.MecanumImpl;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystem.Subsystem;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import java.util.Locale;


public class Drivetrain extends Subsystem {
    public static final String leftFrontName = "Front Left Motor";
    public static final String rightFrontName = "Front Right Motor";
    public static final String leftBackName = "Back Left Motor";
    public static final String rightBackName = "Back Right Motor";
    public DcMotorEx leftFrontDrive   = null;
    public DcMotorEx  rightFrontDrive  = null;
    public DcMotorEx  leftBackDrive   = null;
    public DcMotorEx  rightBackDrive  = null;
    public final static double CONTROLLER_THRESHOLD = 0.04;

    public GoBildaPinpointDriver odo; // Declare OpMode member for the Odometry Computer
    public MecanumDrive roadRunnerController;
    public Follower follower;
    private final boolean ignoreOdo = false;

    private Telemetry telemetry;

    public void init(HardwareMap hardwareMap, Telemetry telemetry, Pose2D initialPose){
        this.telemetry = telemetry;
        initMotors(hardwareMap);
        initOdo(hardwareMap, initialPose);
        initRoadRunner(hardwareMap, initialPose);
        initPedro(hardwareMap, initialPose);
        odo.setPosition(initialPose);

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
    private void initOdo(HardwareMap hardwareMap, Pose2D initialPose){
        if (!ignoreOdo) odo = hardwareMap.get(GoBildaPinpointDriver.class,"odo");
        if (ignoreOdo) return;
        odo.setOffsets(84.0, 0.0, DistanceUnit.MM); //these are tuned for 3110-0002-0001 Product Insight #1
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.REVERSED);
        odo.setPosition(initialPose);
    }
    private void initRoadRunner(HardwareMap hardwareMap, Pose2D initialPose){
        this.roadRunnerController = new MecanumDrive(hardwareMap, initialPose);
    }
    private void initPedro(HardwareMap hardwareMap, Pose2D initialPose){
        follower = Constants.createFollowerRobot(hardwareMap);
        follower.setStartingPose(PoseConverter.pose2DToPose(initialPose, PedroCoordinates.INSTANCE));
    }



    @Override
    public void playOnceImpl(){
        //odo.resetPosAndIMU();
        if (telemetry == null) return;
        telemetry.addData("Status", "Initialized");
        telemetry.addData("X offset", odo.getXOffset(DistanceUnit.MM));
        telemetry.addData("Y offset", odo.getYOffset(DistanceUnit.MM));
        telemetry.addData("Device Version Number:", odo.getDeviceVersion());
        telemetry.addData("Heading Scalar", odo.getYawScalar());
    }
    public void setOdoPositions(double x, double y, double heading){
        odo.setPosition( new Pose2D(DistanceUnit.MM,x, y, AngleUnit.DEGREES, heading));
    }
    public Pose2D getOdoPosition(){
        return odo.getPosition();
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

    public void setDriveToZero(){
        driveRobotRelative(0,0,0);
    }
    public void driveRobotRelative(double forward, double turn, double strafe){
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

    public void driveFieldRelative(double x, double y, double spin){
        double strafe = Math.cos(odo.getHeading(AngleUnit.RADIANS))*x + Math.sin(odo.getHeading(AngleUnit.RADIANS))*y;
        double forward = Math.sin(odo.getHeading(AngleUnit.RADIANS))*x + Math.cos(odo.getHeading(AngleUnit.RADIANS))*y;;
        driveRobotRelative(forward, spin, strafe);
    }

    
    
    public void setPowersRaw(double fls, double frs, double bls, double brs){
        leftFrontDrive.setPower(fls);
        rightFrontDrive.setPower(frs);
        leftBackDrive.setPower(bls);
        rightBackDrive.setPower(brs);
        
    }
    public void setPowersRaw(double fls, double frs, double bls, double brs, double threshold){
        if(Math.abs(leftFrontDrive.getPower() -fls) > threshold) leftFrontDrive.setPower(fls);
        if(Math.abs(leftBackDrive.getPower() -bls) > threshold) leftBackDrive.setPower(bls);
        if(Math.abs(rightFrontDrive.getPower() -frs) > threshold) rightFrontDrive.setPower(frs);
        if(Math.abs(rightBackDrive.getPower() -brs) > threshold) rightBackDrive.setPower(brs);
    }

    public int getEncoderReading(){
        return (leftFrontDrive.getCurrentPosition() 
            + leftBackDrive.getCurrentPosition()
            + rightFrontDrive.getCurrentPosition()
            + rightBackDrive.getCurrentPosition())/4;
    }

    public double getHeading(){
        return odo.getHeading(AngleUnit.DEGREES);
    }

    public void resetPosition(){
        if(ignoreOdo)return;
        odo.resetPosAndIMU(); //resets the position to 0 and recalibrates the IMU
    }
    public void recalibrateIMU(){
        if(ignoreOdo)return;
        odo.recalibrateIMU(); //recalibrates the IMU without resetting position
    }
    @Override
    public void loop(){
        /*
            Request an update from the Pinpoint odometry computer. This checks almost all outputs
            from the device in a single I2C read.
             */
        //telemetry.addData("Wheel Config", leftFrontDrive.getDirection());
        //telemetry.update();
        if(ignoreOdo)return;
        odo.update();
        writeOutPosition(telemetry);
        //String velocity = String.format(Locale.US,"{XVel: %.3f, YVel: %.3f, HVel: %.3f}", odo.getVelX(DistanceUnit.MM), odo.getVelY(DistanceUnit.MM), odo.getHeadingVelocity(UnnormalizedAngleUnit.DEGREES));
        //telemetry.addData("Velocity", velocity);
    }
    public void writeOutPosition(Telemetry telemetry){
        odo.update();
        Pose2D pos = odo.getPosition();
        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(DistanceUnit.MM), pos.getY(DistanceUnit.MM), pos.getHeading(AngleUnit.DEGREES));
        telemetry.addData("Position", data);

    }

}