package com.example.tflitetest;

import java.util.Arrays;
import java.util.Random;

public class Improver {
    public Improver() {
    }

    public static double polyFunction(float x) {
        return Math.pow(x, 5)
                - 10 * Math.pow(x, 4)
                - 10000 * Math.pow(x, 3)
                + 100000 * Math.pow(x, 2)
                + 10000000 * x;
    }

    public static DataPoints originalFunctionDataPoints() {
        float[] X = new float[200];
        float[] Y = new float[200];
        Random random = new Random();

        float maxX = 100;
        float maxY = 0;
        for (int i = 0; i < 200; i++) {
            X[i] = i - 100;
            Y[i] = (float) (polyFunction(X[i]) + random.nextGaussian() * 10000);

            float absY = Math.abs(Y[i]);
            if (absY > maxY) maxY = absY;
        }

        for (int i = 0; i < 200; i++) {
            X[i] = X[i] / maxX;
            Y[i] = Y[i] / maxY;
        }

        return new DataPoints(X, Y);
    }

    public static class DataPoints {
        float[] X;
        float[] Y;

        public DataPoints(float[] x, float[] y) {
            X = x;
            Y = y;
        }
    }
}

