package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Intake extends Subsystem{
    public static final String INTAKE_NAME = "intake";
    private static final double SLOW_INTAKE_POWER = 1.0;

    private JerkLimitedDcMotorEx intakeMotor;

    private double intakePower = 1.0;
    private IntakeState intakeState;
    public void init (HardwareMap hardwareMap){
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, INTAKE_NAME);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor = new JerkLimitedDcMotorEx(motor);
        intakeState = IntakeState.IDLE;
    }

    public void setIdleIntake(){
        intakeState = IntakeState.IDLE;
        intakeMotor.setTargetPower(0.0);
    }
    public void setSlowIntaking(){
        intakeState = IntakeState.SLOW_INTAKING;
        intakeMotor.setTargetPower(SLOW_INTAKE_POWER);
    }
    public void setIntaking(){
        intakeState = IntakeState.INTAKING;
        intakeMotor.setTargetPower(intakePower);
    }
    public void setReverse(){
        if (intakeState != IntakeState.IDLE) return;
        intakeState = IntakeState.REVERSE;
        intakeMotor.setTargetPower(-intakePower);
    }



    @Override
    public void loop(){
        intakeMotor.loop();
        if (intakeState == IntakeState.IDLE) doIdle();
        else if (intakeState == IntakeState.INTAKING) doIntaking();
        else if (intakeState == IntakeState.SLOW_INTAKING) doSlowIntaking();
        else if (intakeState == IntakeState.REVERSE) doReverse();
    }
    private void doIdle() {
    }
    private void doIntaking()   {

    }
    private void doSlowIntaking(){ }
    private void doReverse(){

    }

    public enum IntakeState{
        IDLE, INTAKING, SLOW_INTAKING, REVERSE
    }


    public class JerkLimitedDcMotorEx{
        public DcMotorEx motor;
        private double targetPower;
        private double currentPower;
        private double rampRate; //power / sec
        public static final double DEFAULT_RAMP_RATE = 0.5;
        private ElapsedTime timer;

        public JerkLimitedDcMotorEx(DcMotorEx motor){
            this.motor = motor;
            this.rampRate = DEFAULT_RAMP_RATE;
            timer = new ElapsedTime();
        }

        public void setRampRate(double rampRate){
            this.rampRate = rampRate;
        }
        public void setTargetPower(double targetPower){
            this.targetPower = targetPower;
        }

        public void loop(){
            double time = timer.seconds();
            double change = (targetPower - currentPower);
            if (change < 0 && change <-rampRate*time) change = -rampRate*time;
            else if (change >0 && change > rampRate*time) change = rampRate*time;
            currentPower = currentPower+ change;
            motor.setPower(currentPower);
            timer.reset();
        }

    }

}
