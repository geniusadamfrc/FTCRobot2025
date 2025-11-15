package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.CommandManager;
import org.firstinspires.ftc.teamcode.commands.simple.ManualRobotRelativeMecanumDrive;

@TeleOp(name = "MecanumDrive")
public class MecanumDrive extends OpMode {
    double oldTime = 0;
    double speed = 1000.0;
    double intakeSpeed = 0.8;
    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    @Override
    public void init(){

        Robot.init(hardwareMap, telemetry, gamepad1, gamepad2);
        try {
            CommandManager.registerDefaultCommand(new ManualRobotRelativeMecanumDrive(Robot.gamepadex1.left_stick_y, Robot.gamepadex1.left_stick_x, Robot.gamepadex1.right_stick_x), Robot.drivetrain);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }

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
        Robot.drivetrain.playOnce();
    }
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        //Grabber = hardwareMap.get(Servo.class, "Grabber");
        // Put loop blocks here.
        telemetry.update();
        telemetry.addData("Left Stick", gamepad1.left_stick_y);
        //telemetry.addData("Claw", Grabber.getPosition());
        double forward = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double strafe = gamepad1.left_stick_x;
        //Robot.drivetrain.manualDrive(forward, turn, strafe);
        Robot.update(telemetry);

        /*if (gamepad1.a){
            Robot.drivetrain.resetPosition();
        }
        if (gamepad1.b){
            Robot.drivetrain.recalibrateIMU();
        }*/
        if (gamepad1.a) speed -=1;
        if (gamepad1.y) speed +=1;
        telemetry.addData("Target Speed", speed);
        Robot.shooter.writeSpeeds(telemetry);

        if (gamepad1.left_bumper){
            Robot.shooter.setTargetSpeed(speed);
        } else{
            Robot.shooter.setTargetSpeed(0.0);
        }
        Robot.ramp.setRampPower(gamepad1.left_trigger - gamepad1.right_trigger);
        if (gamepad1.right_bumper) Robot.ramp.setIntakePower(-intakeSpeed);
        else Robot.ramp.setIntakePower(0.0);
        //if (gamepad1.x)intakeSpeed = - intakeSpeed;
    }
    public void doFrequency(){
        /*
            This code prints the loop frequency of the REV Control Hub. This frequency is effected
            by I²C reads/writes. So it's good to keep an eye on. This code calculates the amount
            of time each cycle takes and finds the frequency (number of updates per second) from
            that cycle time.
             */
        double newTime = getRuntime();
        double loopTime = newTime-oldTime;
        double frequency = 1/loopTime;
        oldTime = newTime;
        telemetry.addData("REV Hub Frequency: ", frequency); //prints the control system refresh rate
    }



    public void sampleDrive(){
        /*
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
         */
    }
}