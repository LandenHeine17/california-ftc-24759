package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Gamepad;

public class LauncherController {
    RobotHardware robot;

    public LauncherController(RobotHardware passedRobot) {
        robot = passedRobot;
    }
    double hoodPos = 0.0;

    public void basicLaunch(Gamepad gp) {
        double flywheelSpeed = gp.right_trigger * -1;
        double turretRotate = gp.left_stick_x;

        // Right now there is no reverse so we don't launch into the robot
        robot.flywheel.setPower(flywheelSpeed);
        robot.turret.setPower(turretRotate);
    }

    public void hood(Gamepad gp) {
        if (gp.b) {
            hoodPos -= 0.01;
        } else if (gp.a) {
            hoodPos += 0.01;
        }

        robot.hoodServo.setPosition(hoodPos);
    }
}
