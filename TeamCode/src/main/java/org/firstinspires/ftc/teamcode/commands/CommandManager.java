package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystem.Subsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CommandManager {

    private static HashSet<Command> commands= new HashSet<>();
    private static HashMap<Subsystem, Command> defaultCommands= new HashMap<>();;
    public static boolean BUSY_SUBSYSTEM_INTERRUPT = true; //true for interupt, false will ignore command


    public static void registerDefaultCommand(Command c, Subsystem s) throws Exception {
        if (c.getSubsystems().size() != 1 || c.getSubsystems().get(0) != s){
            throw new Exception("Bad Default Command: " + c.getClass());
        }
        defaultCommands.put(s,c);
    }


    public static boolean schedule(Command command){
        boolean active = false;
        boolean isDefault = false;
        for(Subsystem s : command.getSubsystems()){
            if (s.isActive()) active =true;
            if (s.isDefault()) isDefault = true;
        }
        if (active && !BUSY_SUBSYSTEM_INTERRUPT) return false;
        if (active || isDefault){
            for(Subsystem s : command.getSubsystems()){
                if (s.isActive()) {
                    s.forceExit();
                }
            }
            checkFinish();
        }

        for (Subsystem s : command.getSubsystems()){
            s.setActive(command);
        }
        startCommand(command);
        return true;
    }

    private static void startCommand(Command command){
        commands.add(command);
        command.begin();
    }
    private static void finishCommand(Command c){
        commands.remove(c);
        for(Subsystem s : c.getSubsystems()){
            s.setIdle();
        }
    }


    public static void forceExit(Subsystem s){
        s.forceExit();
    }




    public static void update(){
        for(Command c : commands)
            c.loop();
        checkFinish();
        checkForDefaultCommand();
    }
    private static void checkFinish(){
        List<Command> cc = new ArrayList<>();
        for(Command c : commands){
            if (c.isFinished())
                cc.add(c);
        }
        for(Command c : cc){
            finishCommand(c);
        }
    }

    private static void checkForDefaultCommand(){
        for(Subsystem s : defaultCommands.keySet()){
            if (s.isIdle()){
                Command c = defaultCommands.get(s);
                s.setDefault(c);
                startCommand(c);
            }
        }
    }

}
