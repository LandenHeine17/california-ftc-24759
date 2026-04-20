package org.firstinspires.ftc.teamcode.Hardware.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.Software.Subsystems.TelemetryManager;

public class OuttakeController {
    RobotHardware robot;
    TelemetryManager tel;

    public OuttakeController(RobotHardware passedRobot, TelemetryManager passedTel) {
        robot = passedRobot;
        tel = passedTel;
    }
    double hoodPos = 0.0;

    public void controlFlywheel(Gamepad gp) {
        double flywheelSpeed = gp.right_stick_y * -1;
        if (flywheelSpeed > 0) flywheelSpeed = 0;

        // Right now there is no reverse so we don't launch into the robot
        robot.flywheel.setPower(flywheelSpeed);
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
