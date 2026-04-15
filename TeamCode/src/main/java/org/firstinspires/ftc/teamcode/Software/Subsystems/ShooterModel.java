package org.firstinspires.ftc.teamcode.Software.Subsystems;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import com.qualcomm.hardware.limelightvision.LLResult;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class ShooterModel {
    private Interpreter tflite;
    double lastInferenceTime = 0;
    double lastSpeed = 0;           double lastAngle = 0.38;
    double[] lastReturn = {lastSpeed, lastAngle};
    public double[] getPrediction(LLResult llInfo) {
        if (System.currentTimeMillis() - lastInferenceTime > 100) {
            if (llInfo.isValid()) {
                double ty_ll = llInfo.getTy();
                double ta_ll = llInfo.getTa();
                float ty = (float) ty_ll;
                float ta = (float) ta_ll;

                float[] result = predict(ty, ta);
                lastReturn[0] = (double) result[0];
                lastReturn[1] = (double) result[1];

                lastInferenceTime = System.currentTimeMillis();

            }
        }
        return lastReturn;
    }

    public ShooterModel(AssetManager assetManager) {
        try {
            ByteBuffer modelBuffer = loadModelFile(assetManager, "shooter_model_v2.tflite");
            tflite = new Interpreter(modelBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();

        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();

        ByteBuffer buffer = ByteBuffer.allocateDirect((int) declaredLength);
        buffer.order(ByteOrder.nativeOrder());
        fileChannel.position(startOffset);
        fileChannel.read(buffer);
        buffer.rewind();
        return buffer;
    }

    public float[] predict(float ty, float ta) {
        float[][] input = new float[][]{{ty, ta}};
        float[][] output = new float[1][2];

        tflite.run(input, output);

        return output[0];  // [launcherSpeed, hoodAngle]
    }
}