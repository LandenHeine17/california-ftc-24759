package org.firstinspires.ftc.teamcode.Hardware.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.Software.Subsystems.TelemetryManager;

public class Intake {
    RobotHardware robot;
    TelemetryManager tel;

    public Intake(RobotHardware passedRobot, TelemetryManager passedTel) {
        robot = passedRobot;
        tel = passedTel;
    }

    public void intake(Gamepad gp) {
        double intake = gp.right_trigger - gp.left_trigger;


        robot.intake.setPower(intake);
    }

    public void feeder(Gamepad gp) {
        double feeder = gp.right_trigger - gp.left_trigger;

        robot.feeder.setPower(feeder);
        robot.feedingServo.setPower(-feeder);
    }
}

