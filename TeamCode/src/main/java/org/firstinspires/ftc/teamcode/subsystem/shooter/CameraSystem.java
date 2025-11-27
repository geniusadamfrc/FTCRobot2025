package org.firstinspires.ftc.teamcode.subsystem.shooter;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class CameraSystem {
    private static final boolean USE_WEBCAM = true;

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    private AprilTagDetection lastDetection;
    private double lastBearing;
    private int goalId;
    private int patternId = 0;
    public void init(HardwareMap hardwareMap) {

        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder()

                // The following default settings are available to un-comment and edit as needed.
                //.setDrawAxes(false)
                //.setDrawCubeProjection(false)
                //.setDrawTagOutline(true)
                //.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                //.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                //.setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)

                // == CAMERA CALIBRATION ==
                // If you do not manually specify calibration parameters, the SDK will attempt
                // to load a predefined calibration for your camera.
                .setLensIntrinsics(283, 283, 317, 231.506)
                // ... these parameters are fx, fy, cx, cy.

                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(aprilTag);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Disable or re-enable the aprilTag processor at any time.
        //visionPortal.setProcessorEnabled(aprilTag, true);

    }

    public void setGoalId(int goalId){
        this.goalId  =goalId;
    }


    public void doTelemetry(Telemetry telemetry) {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                //telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        //telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        //telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        //telemetry.addLine("RBE = Range, Bearing & Elevation");

    }


    public double computeRangeToGoal(Telemetry telemetry){
        if (goalId == 0) throw new RuntimeException("Must set goal ID");
        List<AprilTagDetection> aprilTags  = aprilTag.getDetections();
        if (aprilTags.size() ==0){
            if (lastDetection == null) return 0.0;
            return convertRangeToActualDistance(lastDetection.ftcPose.range);
        }
        AprilTagDetection current = null;
        for(AprilTagDetection d : aprilTags){
            if (d.id == goalId){
                current = d;
            }
        }
        if (current == null) return convertRangeToActualDistance(lastDetection.ftcPose.range);
        telemetry.addData("Image Range", current.ftcPose.range);
        lastDetection = current;
        return convertRangeToActualDistance(current.ftcPose.range);
    }
    private double convertRangeToActualDistance(double cameraRange) {
        return cameraRange/0.339;
    }

    public double getBearing() {
        if (goalId == 0) throw new RuntimeException("Must set goal ID");
        List<AprilTagDetection> aprilTags  = aprilTag.getDetections();
        if (aprilTags.size() ==0) return lastBearing;
        AprilTagDetection current = null;
        for(AprilTagDetection d : aprilTags){
            if (d.id == goalId){
                current = d;
            }
        }
        if (current == null) return lastBearing;
        lastBearing = current.ftcPose.bearing;
        return current.ftcPose.bearing;
    }

    public void tryToObtainPatternID(){
        if (patternId > 0 ) return;
        List<AprilTagDetection> aprilTags  = aprilTag.getDetections();
        if (aprilTags.size() ==0) return;
        AprilTagDetection current = null;
        for(AprilTagDetection d : aprilTags){
            if (d.id < 24 && d.id > 20){
                current = d;
            }
        }
        if (current == null) return ;
        patternId = current.id  ;
    }
    public boolean isPatternFound(){
        return patternId >0;
    }
    public int getPatternId(){
        return patternId;
    }
}
