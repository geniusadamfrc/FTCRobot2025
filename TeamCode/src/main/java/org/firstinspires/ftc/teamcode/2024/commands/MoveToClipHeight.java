package org.firstinspires.ftc.teamcode.commands;
import org.firstinspires.ftc.teamcode.ViperSlide;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ElevatorClaw;

public class MoveToClipHeight extends Command{
    private ViperSlide elevator;
    private ElevatorClaw claw;
    public final int CHAMBER_HEIGHT = 1350;
    private Telemetry telemetry;
    public MoveToClipHeight(ViperSlide elevator, ElevatorClaw claw, Telemetry telemetry){
        this.elevator = elevator;
        this.claw = claw;
        this.telemetry = telemetry;
        registerSubsystem(elevator);
    }

    public void beginImpl(){
        //telemetry.addData("Loop Begin", "true");
        elevator.moveElevatorToHeight(CHAMBER_HEIGHT);
    }
    public void loopImpl(){
        //telemetry.addData("Loop Start", "true");
        if (!elevator.isElevatorBusy()){
            claw.openClaw();
            finish();
        }
    }
    // todo: write your code here
}
