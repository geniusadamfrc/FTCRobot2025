
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import java.util.ArrayList;
import java.util.List;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.commands.*;

@TeleOp(name="Aries Drive", group="Robot")
public class AriesDrive extends OpMode{

    /* Declare OpMode members. */
    
    public LinearSlide linearSlide;
    public Drivetrain drivetrain;
    public ViperSlide  elevator;    
    public ElevatorClaw elevatorClaw;
    public SlideClaw slideClaw;
    public IMUSub imu;
    //public Servo    claw  = null;

    double clawOffset = 0;

    public static final double MID_SERVO   =  0.5 ;
    public static final double CLAW_SPEED  = 0.02 ;        // sets rate to move servo
    public static final double ARM_UP_POWER    =  0.50 ;   // Run arm motor up at 50% power
    public static final double ARM_DOWN_POWER  = -0.25 ;   // Run arm motor down at -25% power
    
    private Command activeCommand;
    private boolean leftBumperReleased;
    private boolean rightBumperReleased;
    
    public AriesDrive(){
        //commands = new ArrayList<Command>();
    }
    
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        linearSlide = new LinearSlide();
        linearSlide.init(hardwareMap);
        drivetrain = new Drivetrain();
        drivetrain.init(hardwareMap);
        // Define and Initialize Motors
        elevator = new ViperSlide();
        elevator.init(hardwareMap);
        
        elevatorClaw = new ElevatorClaw();
        elevatorClaw.init(hardwareMap);
        slideClaw = new SlideClaw();
        slideClaw.init(hardwareMap);
        
        imu = new IMUSub();
        imu.init(hardwareMap);
        
      //  attempt = new AttemptChamber(drivetrain, elevator, elevatorClaw, telemetry);
    //    moveToChamberHeight = new MoveToChamberHeight(elevator, elevatorClaw, telemetry);
      //  commands.add(attempt);
    //    commands.add(moveToChamberHeight);

        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press Play.");    //
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        elevator.playOnce();
        elevatorClaw.playOnce();
        slideClaw.playOnce();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        if(gamepad1.back){
            imu.resetYaw();
            return;
        }
        if(gamepad2.back){
            elevator.resetEncoders();
            elevator.raiseElevatorUnrestricted(gamepad2.left_stick_y);
            
        return;
        }
        double forward = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double strafe = gamepad1.left_stick_x;
        
        drivetrain.manualDrive(forward, turn, strafe);
        
        handleGamePad1LeftBumper();
        
        //elevator Controls        
        if ((gamepad1.y||gamepad2.y) && activeCommand == null){
             activeCommand  = new MoveToChamberHeight(elevator, elevatorClaw, telemetry);
             activeCommand.begin();
        }   
        if ((gamepad1.a||gamepad2.a) && activeCommand == null){
            activeCommand = new MoveToWallHeight(elevator, elevatorClaw);
            activeCommand.begin();
        } 
        if (gamepad1.x||gamepad2.x) elevatorClaw.openClaw();
        if (gamepad1.b||gamepad2.b) elevatorClaw.closeClaw();
        


        if (gamepad1.dpad_up||gamepad2.dpad_up) slideClaw.raiseClaw();
        if (gamepad1.dpad_down||gamepad2.dpad_down) slideClaw.lowerClaw();
        if (gamepad1.dpad_left||gamepad2.dpad_left) slideClaw.openClaw();
        if (gamepad1.dpad_right||gamepad2.dpad_right) slideClaw.closeClaw();
        
        if (gamepad2.left_bumper && activeCommand == null){
            activeCommand = new PrepareForIntake(slideClaw);
            activeCommand.begin();
        }
        handleGamePad2RightBumper();
        linearSlide.manuallyMove(gamepad2.right_trigger-gamepad2.left_trigger);
 
        
        
        
        //(gamepad2.right_stick_y);
        elevator.raiseElevator(-gamepad2.left_stick_y);
        elevator.loop();
        //for(Command command : commands){
            //telemetry.addData("Command", "" + command.isFinished());
            //command.loop();
        //}
        if (activeCommand != null) activeCommand.loop();
        if (activeCommand != null && activeCommand.isFinished()) activeCommand = null;
        if (activeCommand != null) telemetry.addData("ActiveCommand Running:", activeCommand.isStarted());
        if (activeCommand != null) telemetry.addData("ActiveCommand Finished:", activeCommand.isFinished());
        
        
    
        
        // Send telemetry message to signify robot running;
       telemetry.addData("Slide Position",  ":" + (linearSlide.linearSlide.getCurrentPosition() - linearSlide.startingPosition));
       telemetry.addData("Elevator Position",  ":" + elevator.getPosition());
       
       telemetry.addData("Elevator Raw Position",  "" +elevator.getRawPosition());
       telemetry.addData("ElevatorStatus: " , elevator.getState());
       telemetry.addData("DrivetrainStatus: " , drivetrain.getState());
       telemetry.addData("Drivetrain Encoder", drivetrain.getEncoderReading()+"");
       
       telemetry.addData("Current Time", "" +System.currentTimeMillis() );
        telemetry.addData("Active Command", activeCommand == null ? "IDLE" : activeCommand.getClass().getName());
        imu.addTelemetry(telemetry);        
           
    }
    
    public void handleGamePad1LeftBumper(){
        if(gamepad1.left_bumper){
            if (activeCommand == null && leftBumperReleased) {
                activeCommand = new AttemptChamber(drivetrain, elevator, elevatorClaw, telemetry);;
                activeCommand.begin();
                leftBumperReleased = false;
            }
        }else {
            leftBumperReleased = true;
            if (activeCommand != null && activeCommand instanceof AttemptChamber &&
                activeCommand.isStarted() && !activeCommand.isFinished())
                    activeCommand.finish();
        }
    }
    public void handleGamePad2RightBumper(){
        if(gamepad2.right_bumper){
            if (activeCommand == null && rightBumperReleased) {
                activeCommand = new Intake(slideClaw, linearSlide);
                activeCommand.begin();
                rightBumperReleased = false;
            }
        }else {
            rightBumperReleased = true;
            if (activeCommand != null && activeCommand instanceof Intake &&
                activeCommand.isStarted() && !activeCommand.isFinished())
                    activeCommand.finish();
        }
    }
    
    
    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
