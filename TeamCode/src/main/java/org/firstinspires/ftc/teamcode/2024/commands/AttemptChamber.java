/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.util.Timer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.ElevatorClaw;
import org.firstinspires.ftc.teamcode.ViperSlide;
import org.firstinspires.ftc.teamcode.Drivetrain;

public class AttemptChamber extends Command{
    private ElevatorClaw claw;
    private ViperSlide elevator;
    private Drivetrain drivetrain;
    private boolean isComplete;
    private TimerCommand timer;
    private Telemetry telemetry;
    public final static double MOVE_BACK_POWER = -0.2;
    public final static int BACKUP_DISTANCE = -300;
    private DriveStraightDistance driveCommand;
    private MoveToClipHeight clipCommand;
    
    public AttemptChamber(Drivetrain drivetrain, ViperSlide elevator, ElevatorClaw claw, Telemetry telemetry){
        this.elevator = elevator;
        this.claw = claw;
        this.drivetrain =  drivetrain;
        registerSubsystem(drivetrain);
        registerSubsystem(elevator);
        registerSubsystem(claw);
        this.telemetry = telemetry;
    }
    @Override
    public void beginImpl(){
        driveCommand = new DriveStraightDistance(drivetrain, MOVE_BACK_POWER, BACKUP_DISTANCE, telemetry);
        clipCommand = new MoveToClipHeight(elevator, claw, telemetry);
        driveCommand.begin();
    }
    public void loopImpl(){
        if (!driveCommand.isFinished()){
            driveCommand.loop(); return;
        }
        if (!clipCommand.isStarted()){
            clipCommand.begin();
            return;
        }
        if (clipCommand.isFinished()){
            finish();
        }else {
            clipCommand.loop();
        }
        /*if (!timer.isFinished()) timer.loop();
        else {
            if (!isComplete){
                claw.setArmPosition(0.3);
                timer = new TimerCommand(TIME_DELAY);
                timer.begin();
                isComplete = true;
            }
            else {
                telemetry.addData("Attempt Chamber ", "Finished");
                finish();
            }
            
        }
        */
    }
    
    
}
