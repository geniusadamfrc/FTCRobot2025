package org.firstinspires.ftc.teamcode.commands;

import java.util.ArrayList;


public class ParallelCommand extends Command{
    private ArrayList<Command> commands; 
    
    public ParallelCommand(){
        commands = new ArrayList<Command>();
    }
    public void addCommand(Command c){
        commands.add(c);
    }

    
    @Override
    public void beginImpl(){
        for (Command c : commands){
            c.begin();
        }
        
    }
    @Override 
    public void loopImpl(){
        boolean allFinished = true;
        for (Command c: commands){
            if (!c.isFinished()){
                c.loop();
                allFinished = false;
            }    
        }
        if (allFinished){
            finish();
        }
        
    }
    
    
    
        // todo: write your code here
}