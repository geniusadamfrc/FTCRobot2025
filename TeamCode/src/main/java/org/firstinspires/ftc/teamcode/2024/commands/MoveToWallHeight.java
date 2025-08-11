package org.firstinspires.ftc.teamcode.commands;
import org.firstinspires.ftc.teamcode.*;

public class MoveToWallHeight extends Command{
    private ViperSlide elevator;
    private ElevatorClaw claw;
    public final int WALL_HEIGHT = 0;

    public MoveToWallHeight(ViperSlide elevator, ElevatorClaw claw){
        this.elevator = elevator;
        this.claw = claw;
        registerSubsystem(elevator);
    }

    public void beginImpl(){
        elevator.moveElevatorToHeight(WALL_HEIGHT);
        claw.centerArm();
    }
    public void loopImpl(){
        if (!elevator.isElevatorBusy()) finish();
    }
    // todo: write your code here
}