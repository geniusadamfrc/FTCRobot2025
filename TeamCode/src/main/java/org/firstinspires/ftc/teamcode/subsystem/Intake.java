package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake extends Subsystem{
    public static final String INTAKE_NAME = "intake";
    private static final double SLOW_INTAKE_POWER = 0.5;

    private DcMotorEx intakeMotor;

    private double intakePower = 1.0;
    private IntakeState intakeState;
    public void init (HardwareMap hardwareMap){
        intakeMotor = hardwareMap.get(DcMotorEx.class, INTAKE_NAME);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeState = IntakeState.IDLE;
    }

    public void setIdleIntake(){
        intakeState = IntakeState.IDLE;
        doIdle();
    }
    public void setSlowIntaking(){
        intakeState = IntakeState.SLOW_INTAKING;
        doSlowIntaking();
    }
    public void setIntaking(){
        intakeState = IntakeState.INTAKING;
        doIntaking();
    }



    @Override
    public void loop(){
        if (intakeState == IntakeState.IDLE) doIdle();
        else if (intakeState == IntakeState.INTAKING) doIntaking();
        else if (intakeState == IntakeState.SLOW_INTAKING) doSlowIntaking();
    }
    private void doIdle() {
        intakeMotor.setPower(0.0);
    }
    private void doIntaking()   {
        intakeMotor.setPower(intakePower);
    }
    private void doSlowIntaking(){ intakeMotor.setPower(SLOW_INTAKE_POWER);}

    public enum IntakeState{
        IDLE, INTAKING, SLOW_INTAKING
    }
}
