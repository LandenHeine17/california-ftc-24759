package org.firstinspires.ftc.teamcode.Hardware.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.Software.Subsystems.TelemetryManager;

public class Drivetrain {
    RobotHardware robot;
    TelemetryManager tel;

    public Drivetrain(RobotHardware passedRobot, TelemetryManager passedTel) {
        robot = passedRobot;
        tel = passedTel;
    }

    public void robotBasedMovement(Gamepad gp) {
        double forward = gp.right_stick_y;
        double rotate = gp.left_stick_x;
        double strafe = gp.right_stick_x;

        double frontLeft = forward - rotate - strafe;
        double frontRight = forward + rotate - strafe;
        double backLeft = forward - rotate + strafe;
        double backRight = forward + rotate + strafe;

        robot.frontLeft.setPower(frontLeft);
        robot.frontRight.setPower(frontRight);
        robot.backLeft.setPower(backLeft);
        robot.backRight.setPower(backRight);
    }
}

