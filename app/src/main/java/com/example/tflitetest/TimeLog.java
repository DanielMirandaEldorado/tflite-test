package com.example.tflitetest;

import android.util.Log;

public class TimeLog {
    private static final String TAG = "TimeLog";
    private static long startTime;

    public static void start() {
        startTime = System.currentTimeMillis();
    }

    public static void log(String title) {
        long diff = System.currentTimeMillis() - startTime;
        Log.d(TAG, title + ": " + diff);
    }
}
