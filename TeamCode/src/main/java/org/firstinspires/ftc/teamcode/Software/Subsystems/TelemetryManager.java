package org.firstinspires.ftc.teamcode.Software.Subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Allows telemetry logging from non-OpMode classes.
 *
 * // Declare a TelemetryManager Object then call init IN YOUR TELEOP CLASS
 * TelemetryManager tel = new TelemetryManager();
 * tel.init(telemetry);
 *
 * // Then create a reference to that object in your subsytem class
 * private TelemetryManager tel;
 * public void setTelemetryManager(TelemetryManager passedTel) {
 *     this.tel = passedTel;
 * }
 */
public class TelemetryManager {
    private Telemetry telemetry;
    public TelemetryManager(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public void log(String caption, Object value) {
        telemetry.addData(caption, value);
    }

    public void log(String caption, String format, Object... args) {
        telemetry.addData(caption, String.format(format, args));
    }

    public void update() {
        telemetry.update();
    }

    public void clear() {
        telemetry.clear();
    }

    public void logLine(String line) {
        telemetry.addLine(line);
    }
}