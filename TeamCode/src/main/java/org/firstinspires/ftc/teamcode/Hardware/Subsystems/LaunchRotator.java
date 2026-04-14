package org.firstinspires.ftc.teamcode.Hardware.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.Software.Constants;
import org.firstinspires.ftc.teamcode.Software.Subsystems.TelemetryManager;


public class LaunchRotator {
    RobotHardware rob;
    TelemetryManager tel;

    // =========== initializations ===========
    double lastPosition = 0;
    private static ElapsedTime tickTimer;
    public Boolean focusIsActive = false;
    double lastTx;
    boolean foundTag = false;
    double lastTime = 0;
    double velocityFiltered = 0;
    double kP = 0.012;              double kD = 0.0002;             double alpha = 0.1;


    public LaunchRotator(RobotHardware passedRobot, TelemetryManager passedTel) {
        rob = passedRobot;
        tel = passedTel;
        tickTimer = new ElapsedTime();
        tickTimer.reset();
    }

    public void launchRotate(Gamepad gp) {
        double turret = gp.left_stick_x;
        double turretRotate = gp.left_stick_x;

        // Right now there is no reverse so we don't launch into the robot
        rob.turret.setPower(turretRotate);
    }

    public void controlLaunchRotate(Gamepad gp, LLResult llInfo) {
        if (gp.dpadUpWasPressed()) {
            focusIsActive = !focusIsActive;
        }
        controlLaunchRotate(focusIsActive, llInfo, 0, Constants.LAUNCHROTATEMAX, Constants.LAUNCHROTATEMIN);
    }

    public void controlLaunchRotate(boolean focusing, LLResult llInfo, double offset, double rotateMax, double rotateMin) {
        double tx = llInfo.getTx();
        double error = tx + offset;
        double ta = llInfo.getTa();
        double power = 0;
        double currentPosition = rob.turret.getCurrentPosition();
        tel.log("Current launcher position", currentPosition);
        tel.log("\nLauncher focusing", focusIsActive ? "Yes" : "NO");
        if (focusing) {
            foundTag = ta > 0;

            if (currentPosition > rotateMax) {
                if (foundTag) {
                    if (tx < 0) {
                        lastTx = tx;
                    } else {
                        tx = 0;
                    }
                } else {
                    tx = -20;
                    lastTx = tx;
                }
            } else if (currentPosition < rotateMin) {
                if (foundTag) {
                    if (tx > 0) {
                        lastTx = tx;
                    } else {
                        tx = 0;
                    }
                } else {
                    tx = 20;
                    lastTx = tx;
                }
            } else {
                if (foundTag) {
                    lastTx = tx;
                } else {
                    tx = lastTx;
                }
            }
            double dt = tickTimer.milliseconds() - lastTime;
            lastTime = tickTimer.milliseconds();
            double velocity = (tx - lastPosition) / dt;
            lastPosition = tx;
            velocityFiltered = alpha * velocity + (1 - alpha) * velocityFiltered;

            power = kP * error - kD * velocityFiltered;
            tel.log("Rotate Position", currentPosition);
            tel.log("Rotate Power", power);
            rob.turret.setPower(power);
        } else {
            rob.turret.setPower(0);
            lastTx = 0;
        }
    }


}
