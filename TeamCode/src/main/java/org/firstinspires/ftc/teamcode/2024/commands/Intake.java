package org.firstinspires.ftc.teamcode.commands;
import org.firstinspires.ftc.teamcode.*;


public class Intake extends SequentialCommand{
    private SlideClaw claw;
    private LinearSlide slide;
    private final static int DELAY_TIME = 500; //in ms
    
    public Intake(SlideClaw claw, LinearSlide slide){
        super();
        this.claw = claw;
        this.slide = slide;
    }
    @Override
    public void beginImpl(){
        addCommand(new LowerClaw(claw));
        addCommand(new CloseClaw(claw));
        addCommand(new RaiseClaw(claw));
        addCommand(new ReturnSlideToHome(slide));
        super.beginImpl();
    } 
        
    
    public class LowerClaw extends Command{
        private SlideClaw claw;
        private TimerCommand timer;
        public LowerClaw(SlideClaw claw){
            this.claw = claw;
            registerSubsystem(claw);
        }
        public void beginImpl(){
            claw.lowerClaw();
            timer = new TimerCommand(DELAY_TIME);
            timer.begin();
        }
        public void loopImpl(){
            if (timer.isFinished())
                finish();
            else 
                timer.loop();
        }
        

    }
    public class RaiseClaw extends Command{
        private SlideClaw claw;
        private TimerCommand timer;
        
        public RaiseClaw(SlideClaw claw){
            this.claw = claw;
            registerSubsystem(claw);
        }
        public void beginImpl(){
            claw.raiseClaw();
            timer = new TimerCommand(DELAY_TIME);
            timer.begin();
        }
        public void loopImpl(){
            if (timer.isFinished())
                finish();
            else 
                timer.loop();
        }
        

    }
    public class CloseClaw extends Command{
        private SlideClaw claw;
        private TimerCommand timer;
        
        public CloseClaw(SlideClaw claw){
            this.claw = claw;
            registerSubsystem(claw);
        }
        public void beginImpl(){
            claw.closeClaw();
            timer = new TimerCommand(DELAY_TIME);
            timer.begin();
        }
        public void loopImpl(){
            if (timer.isFinished())
                finish();
            else 
                timer.loop();
        }
        

    }
    public class OpenClaw extends Command{
        private SlideClaw claw;
        private TimerCommand timer;
        
        public OpenClaw(SlideClaw claw){
            this.claw = claw;
            registerSubsystem(claw);
        }
        public void beginImpl(){
            claw.openClaw();
            timer = new TimerCommand(DELAY_TIME);
            timer.begin();
        }
        public void loopImpl(){
            if (timer.isFinished())
                finish();
            else 
                timer.loop();
        }
        

    }
    
    public class ReturnSlideToHome extends Command{
        private LinearSlide slide;
        
        public ReturnSlideToHome(LinearSlide slide){
            this.slide = slide;
            registerSubsystem(slide);
        }
        public void beginImpl(){
            slide.commandMove(-0.5);
        }
        public void loopImpl(){
            if (slide.commandMove(-0.5) > -0.1)
                finish();
        }
        
    }
}