package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotHardware {
    public DcMotor frontLeft, frontRight, backLeft, backRight;
    public DcMotor intake, feeder, turret;
    public DcMotorEx flywheel;
    public Servo hoodServo, stopper;
    public CRServo feedingServo;
    public Limelight3A limelight;
    public IMU imu;

    public RobotHardware(HardwareMap hardwareMap) {
        // Drivetrain
        this.frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        this.frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        this.backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        this.backRight = hardwareMap.get(DcMotor.class, "backRight");

        // Other Actuators
        this.intake = hardwareMap.get(DcMotor.class, "intake");
        this.feeder = hardwareMap.get(DcMotor.class, "feeder");
        this.flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        this.turret = hardwareMap.get(DcMotor.class, "turret");
        this.hoodServo = hardwareMap.get(Servo.class, "hoodServo");
        this.feedingServo = hardwareMap.get(CRServo.class, "feedingServo");
        this.stopper = hardwareMap.get(Servo.class, "stopper");

        // set drivetrain
        this.frontLeft.setDirection(DcMotor.Direction.REVERSE);
        this.frontRight.setDirection(DcMotor.Direction.REVERSE);
        this.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // turret
        this.turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.turret.setDirection(DcMotor.Direction.REVERSE);

        // launch motor
        this.flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
        this.flywheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // feeder
        this.feeder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.feeder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.feeder.setDirection(DcMotor.Direction.REVERSE);

        // Intake
        this.intake.setDirection(DcMotor.Direction.REVERSE);

        // Limelight
        this.limelight = hardwareMap.get(Limelight3A.class, "limelight");
    }
}
