package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;

import java.util.ArrayList;
import java.util.List;

@TeleOp
public class Checklist extends OpMode {
    private RobotHardware robot;
    int index = 0;
    List<DcMotor> motors = new ArrayList<>();
    List<String> motorNames = new ArrayList<>();
    @Override
    public void init() {
        robot = new RobotHardware(hardwareMap);

        motors.add(robot.frontLeft);
        motors.add(robot.frontRight);
        motors.add(robot.backLeft);
        motors.add(robot.backRight);
        motors.add(robot.intake);
        motors.add(robot.feeder);
        motors.add(robot.turret);

        motorNames.add("frontLeft");
        motorNames.add("frontRight");
        motorNames.add("backLeft");
        motorNames.add("backRight");
        motorNames.add("intake");
        motorNames.add("feeder");
        motorNames.add("turret");
    }

    @Override
    public void loop() {
        if (gamepad1.aWasPressed()) {
            index += 1;
            if (index >= motors.size()) {
                index = 0;
            }
        }

        robot.flywheel.setPower(gamepad2.right_trigger - gamepad2.left_trigger);
        motors.get(index).setPower(gamepad1.right_trigger - gamepad1.left_trigger);

        for (int i = 0; i < motors.size(); i++) {
            if (i == index) {
                telemetry.addLine(">  "+motorNames.get(i));
            } else {
                telemetry.addLine(" "+motorNames.get(i));
            }
        }

        telemetry.update();
    }
}
