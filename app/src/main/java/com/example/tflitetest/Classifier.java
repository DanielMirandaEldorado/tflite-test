package com.example.tflitetest;

import static com.example.tflitetest.MainActivity.DEGREE;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Classifier {
    private static final String TAG = "Classifier";
    private AssetManager assetManager;
    private String modelPath;
    private int inputSize;
    private Interpreter interpreter;

    public Classifier(AssetManager assetManager, String modelPath, int inputSize) {
        this.assetManager = assetManager;
        this.modelPath = modelPath;
        this.inputSize = inputSize;

        Interpreter.Options options = new Interpreter.Options();
        options.setUseNNAPI(true);

        try {
            interpreter = new Interpreter(loadMappedByteBuffer(assetManager, modelPath), options);
        } catch (IOException e) {
            Log.d(TAG, e.getStackTrace().toString());
        }
    }

    public float classify(float input) {
        float[] poly = new float[DEGREE + 1];
        poly[0] = 1;
        for (int j = 1; j < DEGREE + 1; j++) {
            poly[j] = (float) Math.pow(input, j);
        }

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(inputSize * Float.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        for (int i = 0; i < inputSize; i++) {
            byteBuffer.putFloat(poly[i]);
        }

        float[][] output = new float[1][1];
        interpreter.run(byteBuffer, output);
        return output[0][0];
    }

    private MappedByteBuffer loadMappedByteBuffer(AssetManager assetManager, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLenght = fileDescriptor.getDeclaredLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLenght);
    }
}
