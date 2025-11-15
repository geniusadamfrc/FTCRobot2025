package org.firstinspires.ftc.teamcode.subsystem.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.CommandManager;
import org.firstinspires.ftc.teamcode.gamepad.Axis;
import org.firstinspires.ftc.teamcode.subsystem.Subsystem;

import java.util.Locale;


public class Drivetrain extends Subsystem {
    public static final String leftFrontName = "Front Left Motor";
    public static final String rightFrontName = "Front Right Motor";
    public static final String leftBackName = "Back Left Motor";
    public static final String rightBackName = "Back Right Motor";
    private DcMotor  leftFrontDrive   = null;
    private DcMotor  rightFrontDrive  = null;
    private DcMotor  leftBackDrive   = null;
    private DcMotor  rightBackDrive  = null;
    public final static double CONTROLLER_THRESHOLD = 0.04;

    GoBildaPinpointDriver odo; // Declare OpMode member for the Odometry Computer
    private boolean ignoreOdo = false;

    private Telemetry telemetry;

    public void init(HardwareMap hardwareMap, Telemetry telemetry){
        leftFrontDrive  = hardwareMap.get(DcMotor.class, leftFrontName); //0
        rightFrontDrive = hardwareMap.get(DcMotor.class, rightFrontName); //3
        leftBackDrive  = hardwareMap.get(DcMotor.class, leftBackName); //1
        rightBackDrive = hardwareMap.get(DcMotor.class, rightBackName); //2
        
        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left and right sticks forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);

        this.telemetry = telemetry;
        if (!ignoreOdo) odo = hardwareMap.get(GoBildaPinpointDriver.class,"odo");
        
        
    }
    @Override
    public void playOnceImpl(){
        initOdo();
    }
    public void initOdo(){
        if (ignoreOdo) return;
        /*
        Set the odometry pod positions relative to the point that the odometry computer tracks around.
        The X pod offset refers to how far sideways from the tracking point the
        X (forward) odometry pod is. Left of the center is a positive number,
        right of center is a negative number. the Y pod offset refers to how far forwards from
        the tracking point the Y (strafe) odometry pod is. forward of center is a positive number,
        backwards is a negative number.
         */
        odo.setOffsets(84.0, 0.0, DistanceUnit.MM); //these are tuned for 3110-0002-0001 Product Insight #1

        /*
        Set the kind of pods used by your robot. If you're using goBILDA odometry pods, select either
        the goBILDA_SWINGARM_POD, or the goBILDA_4_BAR_POD.
        If you're using another kind of odometry pod, uncomment setEncoderResolution and input the
        number of ticks per unit of your odometry pod.
         */
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        //odo.setEncoderResolution(13.26291192, DistanceUnit.MM);


        /*
        Set the direction that each of the two odometry pods count. The X (forward) pod should
        increase when you move the robot forward. And the Y (strafe) pod should increase when
        you move the robot to the left.
         */
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.REVERSED);


        /*
        Before running the robot, recalibrate the IMU. This needs to happen when the robot is stationary
        The IMU will automatically calibrate when first powered on, but recalibrating before running
        the robot is a good idea to ensure that the calibration is "good".
        resetPosAndIMU will reset the position to 0,0,0 and also recalibrate the IMU.
        This is recommended before you run your autonomous, as a bad initial calibration can cause
        an incorrect starting value for x, y, and heading.
         */
        //odo.recalibrateIMU();
        odo.resetPosAndIMU();

        telemetry.addData("Status", "Initialized");
        telemetry.addData("X offset", odo.getXOffset(DistanceUnit.MM));
        telemetry.addData("Y offset", odo.getYOffset(DistanceUnit.MM));
        telemetry.addData("Device Version Number:", odo.getDeviceVersion());
        telemetry.addData("Heading Scalar", odo.getYawScalar());

    }

    public void driveRobotRelative(double forward, double turn, double strafe){
        drive(forward, turn, strafe);
    }
    public void drive(double forward, double turn, double strafe){
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
        setSpeedsRaw(fls, frs, bls,brs);
        
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
    
    
    
    public void setSpeedsRaw(double fls, double frs, double bls, double brs){
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

            /*
            Optionally, you can update only the heading of the device. This takes less time to read, but will not
            pull any other data. Only the heading (which you can pull with getHeading() or in getPosition().
             */
        //odo.update(GoBildaPinpointDriver.ReadData.ONLY_UPDATE_HEADING);







            /*
            gets the current Position (x & y in mm, and heading in degrees) of the robot, and prints it.
             */
        Pose2D pos = odo.getPosition();
        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(DistanceUnit.MM), pos.getY(DistanceUnit.MM), pos.getHeading(AngleUnit.DEGREES));
        telemetry.addData("Position", data);

            /*
            gets the current Velocity (x & y in mm/sec and heading in degrees/sec) and prints it.
             */
        String velocity = String.format(Locale.US,"{XVel: %.3f, YVel: %.3f, HVel: %.3f}", odo.getVelX(DistanceUnit.MM), odo.getVelY(DistanceUnit.MM), odo.getHeadingVelocity(UnnormalizedAngleUnit.DEGREES));
        telemetry.addData("Velocity", velocity);


            /*
            Gets the Pinpoint device status. Pinpoint can reflect a few states. But we'll primarily see
            READY: the device is working as normal
            CALIBRATING: the device is calibrating and outputs are put on hold
            NOT_READY: the device is resetting from scratch. This should only happen after a power-cycle
            FAULT_NO_PODS_DETECTED - the device does not detect any pods plugged in
            FAULT_X_POD_NOT_DETECTED - The device does not detect an X pod plugged in
            FAULT_Y_POD_NOT_DETECTED - The device does not detect a Y pod plugged in
            FAULT_BAD_READ - The firmware detected a bad I²C read, if a bad read is detected, the device status is updated and the previous position is reported
            */
        telemetry.addData("Status", odo.getDeviceStatus());

        telemetry.addData("Pinpoint Frequency", odo.getFrequency()); //prints/gets the current refresh rate of the Pinpoint
        //telemetry.update();
    }







}