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
        double intake = gp.right_stick_y;


        robot.intake.setPower(intake);
    }

    public void feeder(Gamepad gp) {
        double feeder = gp.left_stick_y;

        robot.feeder.setPower(feeder);
        robot.feedingServo.setPower(-feeder);
    }
}

