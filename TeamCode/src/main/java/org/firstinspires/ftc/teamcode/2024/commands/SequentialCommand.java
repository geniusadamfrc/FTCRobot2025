package org.firstinspires.ftc.teamcode.commands;


public class SequentialCommand extends Command{
    private LinkedNode first;
    private LinkedNode active;
    private LinkedNode last;
    
    
    @Override
    public void beginImpl(){
        if (first == null){ finish(); return;}
        first.c.begin();
        active = first;
    }
    @Override 
    public void loopImpl(){
        if (active.c.isFinished()){
            if (active.next != null){
            active = active.next;
            active.c.begin();
            }else finish();
        }else{
            active.c.loop();
        }
    }
    
    public void addCommand(Command c){
        if (first == null){
            first = new LinkedNode(c);
            last = first;
        }else {
            LinkedNode current = new LinkedNode(c);
            last.next= current;
            last = current;
        }
    }
    
    
    private class LinkedNode{
        public LinkedNode(Command c){
            this.c = c;
        }
        public Command c;
        public LinkedNode next;
    }
        // todo: write your code here
}