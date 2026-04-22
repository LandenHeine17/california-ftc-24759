package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Hardware.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Hardware.Subsystems.LaunchRotator;
import org.firstinspires.ftc.teamcode.Hardware.Subsystems.OuttakeController;
import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.Software.Subsystems.Limelight;
import org.firstinspires.ftc.teamcode.Software.Subsystems.TelemetryManager;

@TeleOp
public class AITraining extends OpMode {
    private RobotHardware rob;
    private TelemetryManager tel;
    private Drivetrain drivetrain;
    // Low‑pass filter state
    private double filteredShooterSpeed = 0;
    private double filteredHoodAngle = 0;
    private double filteredTa = 0;
    private double filteredTy = 0;

    // Low‑pass smoothing factor (0.0–1.0)
    private final double alpha = 0.05;

    private LaunchRotator turret;
    private Limelight limelight;
    private Intake intake;
    private OuttakeController outtake;
    private LLResult llInfo;
    @Override
    public void init() {
        tel = new TelemetryManager(telemetry);
        rob = new RobotHardware(hardwareMap);

        limelight = new Limelight(rob, tel);
        drivetrain = new Drivetrain(rob, tel);
        intake = new Intake(rob, tel);
        outtake = new OuttakeController(rob, tel);
        turret = new LaunchRotator(rob, tel);

    }
    public void start() {
        limelight.start(0);
    }

    @Override
    public void loop() {
        llInfo = limelight.getInfo();

        drivetrain.robotBasedMovement(gamepad1);
        intake.intake(gamepad2);
        intake.feeder(gamepad2);
        outtake.controlFlywheel(gamepad2);
        outtake.hood(gamepad2);

        turret.controlLaunchRotate(gamepad2, llInfo);


        // Limelight values
        double ty = llInfo.getTy();
        double ta = llInfo.getTa();
        double shooterSpeed = rob.flywheel.getVelocity();
        double hoodAngle = rob.hoodServo.getPosition();

        filteredShooterSpeed = alpha * shooterSpeed + (1 - alpha) * filteredShooterSpeed;
        filteredHoodAngle   = alpha * hoodAngle   + (1 - alpha) * filteredHoodAngle;
        filteredTa = alpha * ta + (1 - alpha) * filteredTa;
        filteredTy   = alpha * ty   + (1 - alpha) * filteredTy;


        telemetry.addData("Limelight is Valid ", llInfo.isValid());


        telemetry.addData("Hood Angle   \t", filteredHoodAngle);
        telemetry.addData("Shooter Speed\t", filteredShooterSpeed);
        telemetry.addData("Ta           \t\t", filteredTa);
        telemetry.addData("Ty           \t\t", filteredTy);


        telemetry.update();
    }
}
