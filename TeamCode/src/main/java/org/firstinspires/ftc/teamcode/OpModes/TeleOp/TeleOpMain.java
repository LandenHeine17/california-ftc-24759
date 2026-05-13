package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

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
public class TeleOpMain extends OpMode {
    private RobotHardware rob;
    private TelemetryManager tel;
    private Drivetrain drivetrain;
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

        telemetry.addData("Tx", llInfo.getTx());
        telemetry.addData("Ty", llInfo.getTy());
        telemetry.addData("Ta", llInfo.getTa());
        telemetry.addData("Is valid: ", llInfo.isValid());

        telemetry.update();
    }
}
