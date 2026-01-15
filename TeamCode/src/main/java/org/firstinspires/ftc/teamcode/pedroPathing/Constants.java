package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.subsystem.drivetrain.Drivetrain;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(20)
            .forwardZeroPowerAcceleration(-32.43)
            .lateralZeroPowerAcceleration(-69.35);



    public static PathConstraints pathConstraints = new PathConstraints(
            0.99,
            300,
            1,
            1)
            ;

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName(Drivetrain.rightFrontName)
            .rightRearMotorName(Drivetrain.rightBackName)
            .leftRearMotorName(Drivetrain.leftBackName)
            .leftFrontMotorName(Drivetrain.leftFrontName)
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .xVelocity(64.16)
            .yVelocity(51.86)
            ;

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(84/25.4)
            .strafePodX(0)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("odo")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);

    public static Follower createFollower(HardwareMap hardwareMap) {
        //MecanumImpl mc = new MecanumImpl(hardwareMap,new MecanumConstants());
        //pathConstraints.setHeadingConstraint(1.0);
        pathConstraints.setVelocityConstraint(0.01);
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                //.setDrivetrain(mc)
                .pinpointLocalizer(localizerConstants)
                .build();
    }
    public static Follower createFollowerRobot(HardwareMap hardwareMap, MecanumImpl mc) {
        PinpointLocalizer localizer = new PinpointLocalizer(localizerConstants);
        //pathConstraints.setHeadingConstraint(1.0);
        pathConstraints.setVelocityConstraint(0.01);
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .setDrivetrain(mc)
                .setLocalizer(localizer)
                .build();
    }
    public static MecanumImpl createMecanumImpl(HardwareMap hardwareMap){
        MecanumImpl mc = new MecanumImpl(hardwareMap,new MecanumConstants());
        return mc;
    }

}