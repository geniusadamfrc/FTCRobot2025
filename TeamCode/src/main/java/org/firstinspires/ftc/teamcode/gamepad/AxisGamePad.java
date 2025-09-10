package org.firstinspires.ftc.teamcode.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;

public class AxisGamePad implements Axis{
    private final AxisType axisType;
    private final Gamepad gamePad;

    public AxisGamePad (AxisType axisType, Gamepad gamepad){
        this.axisType = axisType;
        this.gamePad = gamepad;
    }


    public float getState(){
        if (axisType == AxisType.left_stick_x) return gamePad.left_stick_x;
        if (axisType == AxisType.left_stick_y) return gamePad.left_stick_y;

        if (axisType == AxisType.right_stick_x) return gamePad.right_stick_x;
        if (axisType == AxisType.right_stick_y) return gamePad.right_stick_y;
        if (axisType == AxisType.left_trigger) return gamePad.left_trigger;
        if (axisType == AxisType.right_trigger) return gamePad.right_trigger;

        return 0.0F;
    }
    public enum AxisType{
        left_stick_x,
        left_stick_y,
        right_stick_x,
        right_stick_y,
        left_trigger,
        right_trigger
    }

}
