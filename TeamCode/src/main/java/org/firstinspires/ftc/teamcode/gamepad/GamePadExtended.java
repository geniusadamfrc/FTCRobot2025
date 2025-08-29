package org.firstinspires.ftc.teamcode.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;
import java.util.List;


public class GamePadExtended {

    private final Gamepad gamepad;
    private List<Button> buttons;
    public GamePadExtended(Gamepad gamepad){
        this.gamepad = gamepad;
        buttons = new ArrayList<>();
        initialize();

    }
    private void initialize(){
        a = new Button(Button.ButtonType.a, gamepad);
        b = new Button(Button.ButtonType.b, gamepad);
        x = new Button(Button.ButtonType.x, gamepad);
        y = new Button(Button.ButtonType.y, gamepad);

        dpad_up = new Button(Button.ButtonType.dpad_up, gamepad);
        dpad_down = new Button(Button.ButtonType.dpad_down, gamepad);
        dpad_left = new Button(Button.ButtonType.dpad_left, gamepad);
        dpad_right = new Button(Button.ButtonType.dpad_right, gamepad);

        left_bumper = new Button(Button.ButtonType.left_bumper, gamepad);
        right_bumper= new Button(Button.ButtonType.right_bumper, gamepad);


        buttons.add(a);
        buttons.add(b);
        buttons.add(x);
        buttons.add(y);

        buttons.add(dpad_up);
        buttons.add(dpad_down);
        buttons.add(dpad_right);
        buttons.add(dpad_left);

        buttons.add(left_bumper);
        buttons.add(right_bumper);



        left_stick_y = new Axis(Axis.AxisType.left_stick_y, gamepad);
        left_stick_x = new Axis(Axis.AxisType.left_stick_x, gamepad);
        right_stick_y = new Axis(Axis.AxisType.right_stick_y, gamepad);
        right_stick_x = new Axis(Axis.AxisType.right_stick_x, gamepad);
        left_trigger = new Axis(Axis.AxisType.left_trigger, gamepad);
        right_trigger = new Axis(Axis.AxisType.right_trigger, gamepad);

    }

    public Axis left_stick_y;
    public Axis left_stick_x;
    public Axis right_stick_y;
    public Axis right_stick_x;

    public Axis left_trigger;
    public Axis right_trigger;


    public Button a;
    public Button b;
    public Button x;
    public Button y;
    public Button dpad_up;
    public Button dpad_down;
    public Button dpad_left;
    public Button dpad_right;
    public Button left_bumper;
    public Button right_bumper;




    public void update(){
        for(Button b : buttons) b.update();

    }
}
