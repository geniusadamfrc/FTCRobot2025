package org.firstinspires.ftc.teamcode.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.commands.Command;

public class Button {
    private final ButtonType buttonType;
    private final Gamepad gamePad;
    private Command command;
    private PressType pressType;

    private boolean wasPressed;
    private boolean commandRunning;

    public Button (ButtonType buttonType, Gamepad gamepad){
        this.buttonType = buttonType;
        this.gamePad = gamepad;
        wasPressed = false;
    }

    public boolean isPressed(){
        return getButtonState();
    }
    public void assignCommand(Command c, PressType pressType)
    {
        this.command  =c;
        this.pressType = pressType;
    }

    public void update(){
        if (pressType == PressType.ON_PRESS){
            if(isPressed() && !wasPressed) command.begin();
        }
        if (pressType == PressType.HOLD){
            if (isPressed()){
                if (!wasPressed && !commandRunning) {
                    command.begin();
                    commandRunning = true;
                }
            }
            else if (commandRunning){
                command.interrupt();
                commandRunning = false;
            }
        }
        if (pressType == PressType.ON_RELEASE){
            if (wasPressed && !isPressed())
                command.begin();
        }
        wasPressed = isPressed();
    }




    private boolean getButtonState(){
        if (buttonType == ButtonType.left_stick_button) return gamePad.left_stick_button;
        if (buttonType == ButtonType.right_stick_button) return gamePad.right_stick_button;

        if (buttonType == ButtonType.dpad_up) return gamePad.dpad_up;
        if (buttonType == ButtonType.dpad_down) return gamePad.dpad_down;
        if (buttonType == ButtonType.dpad_left) return gamePad.dpad_left;
        if (buttonType == ButtonType.dpad_right) return gamePad.dpad_right;

        if (buttonType == ButtonType.a) return gamePad.a;
        if (buttonType == ButtonType.b) return gamePad.b;
        if (buttonType == ButtonType.x) return gamePad.x;
        if (buttonType == ButtonType.y) return gamePad.y;

        if (buttonType == ButtonType.guide) return gamePad.guide;
        if (buttonType == ButtonType.start) return gamePad.start;
        if (buttonType == ButtonType.back) return gamePad.back;

        if (buttonType == ButtonType.left_bumper) return gamePad.left_bumper;
        if (buttonType == ButtonType.right_bumper) return gamePad.right_bumper;

        if (buttonType == ButtonType.touchpad) return gamePad.touchpad;
        if (buttonType == ButtonType.touchpad_finger_1) return gamePad.touchpad_finger_1;
        if (buttonType == ButtonType.touchpad_finger_2) return gamePad.touchpad_finger_2;

        return false;
    }
    public enum ButtonType{
        touchpad, touchpad_finger_1, touchpad_finger_2,
        left_stick_button, right_stick_button,
        dpad_up, dpad_down, dpad_left, dpad_right,
        a, b, x, y,
        guide, start, back,
        left_bumper, right_bumper
    }

    public enum PressType{
        HOLD, ON_PRESS, ON_RELEASE
    }
}
