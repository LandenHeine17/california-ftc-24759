package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.ftc.drivetrains.Mecanum;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.math.Vector;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CustomMecanum extends Mecanum {
    private final DcMotorEx frontLeft;
    private final DcMotorEx frontRight;
    private final DcMotorEx backLeft;
    private final DcMotorEx backRight;

    public CustomMecanum(HardwareMap hardwareMap, MecanumConstants constants) {
        super(hardwareMap, constants);

        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public double[] calculateDrive(Vector correctivePower, Vector headingPower, Vector pathingPower, double robotHeading) {
        double forward = correctivePower.getXComponent() + pathingPower.getXComponent();
        double strafe = correctivePower.getYComponent() + pathingPower.getYComponent();
        double rotate = headingPower.getMagnitude() * Math.signum(headingPower.getYComponent());

        double[] powers = new double[4];
        powers[0] = forward - rotate - strafe; // frontLeft
        powers[1] = forward - rotate + strafe; // backLeft
        powers[2] = forward + rotate - strafe; // frontRight
        powers[3] = forward + rotate + strafe; // backRight

        double max = Math.max(Math.max(Math.abs(powers[0]), Math.abs(powers[1])),
                Math.max(Math.abs(powers[2]), Math.abs(powers[3])));
        if (max > 1) {
            powers[0] /= max;
            powers[1] /= max;
            powers[2] /= max;
            powers[3] /= max;
        }

        return powers;
    }

    @Override
    public void runDrive(double[] drivePowers) {
        frontLeft.setPower(-drivePowers[0]);
        backLeft.setPower(-drivePowers[1]);
        frontRight.setPower(-drivePowers[2]);
        backRight.setPower(-drivePowers[3]);
    }
}