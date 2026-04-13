package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Intake {
    RobotHardware robot;

    public Intake(RobotHardware passedRobot) {
        robot = passedRobot;
    }

    public void intake(Gamepad gp) {
        double moveIntake = gp.right_stick_y;


        robot.intake.setPower(moveIntake);
    }

    public void feeder(Gamepad gp) {
        double feeder = gp.left_stick_y;

        robot.feeder.setPower(feeder);
        robot.feedingServo.setPower(feeder * -1);
    }
}

