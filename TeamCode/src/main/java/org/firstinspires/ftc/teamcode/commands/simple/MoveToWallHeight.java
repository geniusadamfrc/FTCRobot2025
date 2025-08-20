package org.firstinspires.ftc.teamcode.commands.simple;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystem.ViperSlide;

public class MoveToWallHeight extends Command {
    private ViperSlide elevator;
    //private ElevatorClaw claw;
    public final int WALL_HEIGHT = 0;

    public MoveToWallHeight(ViperSlide elevator){
        this.elevator = elevator;
        //this.claw = claw;
        registerSubsystem(elevator);
    }

    public void beginImpl(){
        elevator.moveElevatorToHeight(WALL_HEIGHT);
        //claw.centerArm();
    }
    public void loopImpl(){
        if (!elevator.isElevatorBusy()) finish();
    }
    // todo: write your code here
}