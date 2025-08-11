package org.firstinspires.ftc.teamcode.commands;
import org.firstinspires.ftc.teamcode.ViperSlide;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ElevatorClaw;

public class MoveToChamberHeight extends Command{
    private ViperSlide elevator;
    private ElevatorClaw claw;
    public final int CHAMBER_HEIGHT = 2400;
    public final double CLAW_POSITION = 0.40;
    public final double SPEED = 1.0;
    private Telemetry telemetry;
    public MoveToChamberHeight(ViperSlide elevator, ElevatorClaw claw, Telemetry telemetry){
        this.elevator = elevator;
        this.claw = claw;
        this.telemetry = telemetry;
        registerSubsystem(elevator);
    }

    public void beginImpl(){
        //telemetry.addData("Loop Begin", "true");
        elevator.moveElevatorToHeight(CHAMBER_HEIGHT, SPEED);
    }
    public void loopImpl(){
        //telemetry.addData("Loop Start", "true");
        if (!elevator.isElevatorBusy()){
            claw.setArmPosition(CLAW_POSITION);
            finish();
        }
    }
    // todo: write your code here
}
