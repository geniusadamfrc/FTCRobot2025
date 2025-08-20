package org.firstinspires.ftc.teamcode.commands.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.subsystem.*;

@Autonomous(name="Robot: Auto Double Chamber", group="Robot")
public class AutoDoubleChamber extends OpMode {
    private SequentialCommand sequence;
    
    //private List<Command> commands;
    
    private Drivetrain drivetrain;

    private ViperSlide elevator;
    private SlideClaw slideClaw;

    public final static int INITIAL_DELAY = 0;
    public final static int WAIT_BEFORE_DRIVE = 1000;
    public final static int AFTER_CLIP_DELAY = 500;
    public final static int WAIT_CLIP_DELAY = 1000;
    public final static int GRAB_BACKUP_DELAY = 300;
    
    public final static double DRIVE_FORWARD_SPEED = 0.5;
    public final static double CREEP_SPEED = 0.2;
    public final static double TURN_SPEED = 0.3;
    
    public final static int DRIVE_FORWARD_DISTANCE = 2000;
    public final static int FIRST_BACKUP_DISTANCE = 700;
    public final static int DRIVE_FORWARD_DISTANCE_2 = 1600;
    public final static int STRAFE_DISTANCE = 2200;
    public final static int RETURN_STRAFE_DISTANCE = 1800;
    public final static int BACKUP_TIME= 900;
    
    @Override
    public void init() {
        

        
            
        sequence = new SequentialCommand();
        sequence.addCommand(new TimerCommand(INITIAL_DELAY));
        
        ParallelCommand pc1 = new ParallelCommand();
        //pc1.addCommand(new MoveToChamberHeight(elevator, elevatorClaw, telemetry));
        SequentialCommand sq1 = new SequentialCommand();
        sq1.addCommand(new TimerCommand(WAIT_BEFORE_DRIVE));
        //sq1.addCommand(new DriveStraightDistance(drivetrain, DRIVE_FORWARD_SPEED, DRIVE_FORWARD_DISTANCE,  telemetry));
        pc1.addCommand(sq1);
        sequence.addCommand(pc1);
        
        //sequence.addCommand(new AttemptChamber(drivetrain, elevator, elevatorClaw, telemetry));
        sequence.addCommand(new TimerCommand(AFTER_CLIP_DELAY));
        //sequence.addCommand(new DriveStraightDistance(drivetrain, -DRIVE_FORWARD_SPEED, -FIRST_BACKUP_DISTANCE, telemetry));
        ParallelCommand pc = new ParallelCommand();
        //pc.addCommand(new MoveToWallHeight(elevator, elevatorClaw));
        //pc.addCommand(new Turn90Degrees(drivetrain, imu, TURN_SPEED, -90, telemetry));
        sequence.addCommand(pc);
        //sequence.addCommand(new DriveStraightDistance(drivetrain, DRIVE_FORWARD_SPEED,  STRAFE_DISTANCE, telemetry));
        //sequence.addCommand(new Turn90Degrees(drivetrain, imu, TURN_SPEED, -90, telemetry));
        //sequence.addCommand(new DriveStraight(drivetrain, CREEP_SPEED, 0,BACKUP_TIME, telemetry));
        sequence.addCommand(new TimerCommand(WAIT_CLIP_DELAY));
        //sequence.addCommand(new CloseClaw(elevatorClaw, telemetry));
        
        ParallelCommand pc2 = new ParallelCommand ();
        //pc2.addCommand(new MoveToChamberHeight(elevator, elevatorClaw, telemetry));
        SequentialCommand sq = new SequentialCommand();
        sq.addCommand(new TimerCommand(GRAB_BACKUP_DELAY));
        //sq.addCommand(new DriveStraight(drivetrain, -CREEP_SPEED, 0, BACKUP_TIME, telemetry));
        //sq.addCommand(new Turn90Degrees(drivetrain, imu, TURN_SPEED, -90, telemetry));
        //sq.addCommand(new DriveStraightDistance(drivetrain, DRIVE_FORWARD_SPEED, RETURN_STRAFE_DISTANCE, telemetry));
        pc2.addCommand(sq);
        sequence.addCommand(pc2);
        //sequence.addCommand(new Turn90Degrees(drivetrain, imu, TURN_SPEED, -90, telemetry));
        //sequence.addCommand(new DriveStraightDistance(drivetrain, DRIVE_FORWARD_SPEED, DRIVE_FORWARD_DISTANCE_2,  telemetry));
        //sequence.addCommand(new AttemptChamber(drivetrain, elevator, elevatorClaw, telemetry));
        
        sequence.addCommand(new TimerCommand(AFTER_CLIP_DELAY));
        //sequence.addCommand(new DriveStraightDistance(drivetrain, -DRIVE_FORWARD_SPEED, -FIRST_BACKUP_DISTANCE, telemetry));
        ParallelCommand pc3 = new ParallelCommand();
        //pc3.addCommand(new Turn90Degrees(drivetrain, imu, TURN_SPEED, -90, telemetry));
        //pc3.addCommand(new MoveToWallHeight(elevator, elevatorClaw));
        sequence.addCommand(pc3);
        //sequence.addCommand(new DriveStraightDistance(drivetrain, DRIVE_FORWARD_SPEED,  STRAFE_DISTANCE, telemetry));
        //sequence.addCommand(new Turn90Degrees(drivetrain, imu, TURN_SPEED, -90, telemetry));
        //sequence.addCommand(new DriveStraight(drivetrain, CREEP_SPEED, 0,BACKUP_TIME, telemetry));
        

    }
    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        //elevatorClaw.closeClaw();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        sequence.begin();
        //elevator.playOnce();
        //elevatorClaw.playOnce();
        
        //DriveStraight
        
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        if (!sequence.isFinished()){
            sequence.loop(); return;
        }
    
    }
}
