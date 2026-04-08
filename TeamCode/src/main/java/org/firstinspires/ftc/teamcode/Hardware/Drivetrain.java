package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Drivetrain {
    RobotHardware robot;

    public Drivetrain(RobotHardware passedRobot) {
        robot = passedRobot;
    }

   public void robotBasedMovement(Gamepad gp) {
        double forward = gp.right_stick_y;
        double rotate = gp.left_stick_x;
        double strafe = gp.right_stick_x;

        double frontLeft = forward + rotate + strafe;
        double frontRight = forward - rotate - strafe;
        double backLeft = forward + rotate - strafe;
        double backRight = forward - rotate + strafe;

        robot.frontLeft.setPower(frontLeft);
        robot.frontRight.setPower(frontRight);
        robot.backLeft.setPower(backLeft);
        robot.backRight.setPower(backRight);
   }
}

