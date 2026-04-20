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
    private static ElapsedTime tickTimer;

    public Boolean focusIsActive = false;
    // =========== initializations ===========
    double lastPosition = 0;
    double lastTx;
    double lastTime = 0;
    double integral = 0;                double integralMax = 1000;
    double velocityFiltered = 0;        double alpha = 0.15; // low pass filter

    // PID & tuning
    int pidSelect = 0; // 0=P, 1=I, 2=D
    double step = 0.0001; // default step
    double kP = 0.012;          double kD = 0.0002;        double kI = 0.00001;




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


        if (gp.xWasPressed()) {pidSelect = (pidSelect + 1) % 3;}
        if (gp.bWasPressed()) {pidSelect = (pidSelect + 2) % 3;} // backwards

        if (gp.dpadLeftWasPressed()) {step /= 10;}   // finer
        if (gp.dpadRightWasPressed()) {step *= 10;}  // coarser

        if (gp.rightBumperWasReleased()) {
            if (pidSelect == 0) kP += step;
            if (pidSelect == 1) kI += step;
            if (pidSelect == 2) kD += step;
        }
        if (gp.leftBumperWasReleased()) {
            if (pidSelect == 0) kP -= step;
            if (pidSelect == 1) kI -= step;
            if (pidSelect == 2) kD -= step;
        }

        String[] names = {"kP", "kI", "kD"};
        double[] vals = {kP, kI, kD};

        for (int i = 0; i < 3; i++) {
            String indicator = "";
            if (i == pidSelect) indicator = " > ";
            String outputString = indicator + names[i] + ": " + vals[i];
            tel.log(outputString);
        }

        tel.log("Step", step);

        if (focusIsActive) {
            controlLaunchRotate(focusIsActive, llInfo, 0, Constants.LAUNCHROTATEMAX, Constants.LAUNCHROTATEMIN);
        } else {
            rob.turret.setPower(gp.right_stick_x/4);
        }
    }

    public void controlLaunchRotate(boolean focusing, LLResult llInfo, double offset, double rotateMax, double rotateMin) {
        // get motor and tag positions
        double tx = llInfo.getTx();
        double currentPosition = rob.turret.getCurrentPosition();

        // telemetry
        tel.log("\n ===== Launcher ==== \nLauncher focusing", focusIsActive ? "Yes" : "NO");
        tel.log("Current launcher position", currentPosition);

        // run if focusing is active
        if (focusing) {
            // detect if the tag is in frame
            boolean foundTag = llInfo.isValid();

            if (foundTag) {
                if (currentPosition > rotateMax || currentPosition < rotateMin) {
                    tx = 0;
                }
            } else {
                if (currentPosition > rotateMax) {
                    tx = 20;
                } else if (currentPosition < rotateMin) {
                    tx = -20;
                } else {
                    tx = lastTx;
                }
            }

            lastTx = tx;

            // time
            double time = tickTimer.milliseconds();
            double dt = time - lastTime;
            lastTime = time;

            // proportional control
            double error = tx + offset;

            // integral control
            integral += error;
            integral = Math.max(Math.min(integral, integralMax), -integralMax); // clamp integral

            // derivative control
            double velocity = (currentPosition - lastPosition) / dt;
            lastPosition = currentPosition;

            // filter velocity
            velocityFiltered = alpha * velocity + (1 - alpha) * velocityFiltered;

            // define PID terms
            double pTerm = kP * error;
            double iTerm = kI * integral;
            double dTerm = kD * velocityFiltered;
            // define power
            double power = pTerm + iTerm + dTerm;

            // log values
            tel.log("ERR / TX        \t", String.format("%9.3f | %9.3f", error, tx));
            tel.log("P / I / D       \t", String.format("%9.3f | %9.3f | %9.3f", pTerm, iTerm, dTerm));
            tel.log("Pos / Vel / VelF\t", String.format("%9.3f | %9.3f | %9.3f", currentPosition, velocity, velocityFiltered));
            tel.log("dt (ms)", dt);
            tel.log("Rotate Power", power);

            // set power
            rob.turret.setPower(power);
        } else {
            // no power if not focusing
            rob.turret.setPower(0);
            lastTx = 0;
        }
    }
}
