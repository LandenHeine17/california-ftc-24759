package org.firstinspires.ftc.teamcode.Software.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.Software.TeamColor;

public class Limelight{
    RobotHardware rob;
    TelemetryManager tel;

    public Limelight(RobotHardware passedRob, TelemetryManager passedTel){
        this.rob = passedRob;
        this.tel = passedTel;
    }

    public TeamColor pipelineSwitchBlue(Gamepad gamepad, TeamColor currentColor) {
        if (gamepad.dpadUpWasPressed()) {
            // SWITCH PIPELINE TO BLUE
            rob.limelight.pipelineSwitch(1);//blue
            rob.limelight.start();
            return TeamColor.BLUE;
        } else if (gamepad.dpadLeftWasPressed()) {
            // SWITCH PIPELINE TO RED
            rob.limelight.pipelineSwitch(0);//red
            rob.limelight.start();
            return TeamColor.RED;
        }
        else return currentColor;
    }

    public void start(int pipelineNum){
        rob.limelight.pipelineSwitch(pipelineNum);
        rob.limelight.start();
    }

    public LLResult  getInfo() {
        return rob.limelight.getLatestResult();
    }
}