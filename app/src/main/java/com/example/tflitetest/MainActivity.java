package com.example.tflitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MainActivity extends AppCompatActivity {
    public static final int DEGREE = 5;
    public static final String MODEL_PATH = "model.tflite";

    private LineChart chart;
    private LineData lineData;
    private Classifier classifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chart = findViewById(R.id.chart1);
        lineData = new LineData();
//        setLineChartData();

        classifier = new Classifier(getAssets(), MODEL_PATH, DEGREE + 1);
        createGraph(100);
        Improver.DataPoints dataPoints = Improver.originalFunctionDataPoints();
        setData(Color.RED, dataPoints.X, dataPoints.Y);

        chart.setData(lineData);
    }

    private void createGraph(int numPoints) {
        float[] X = new float[numPoints];
        float[] Y = new float[numPoints];

        for (int i = 0; i < numPoints; i++) {
            X[i] = (float) (-1 + Math.random() * 2);

            Y[i] = classifier.classify(X[i]);
        }
        setData(Color.BLUE, X, Y);
    }

    private void setData(int color, float[] X, float[] Y) {
        List<Entry> linevalues = new ArrayList<>();

        for (int i = 0; i < X.length; i++) {
            linevalues.add(new Entry(X[i], Y[i]));
        }

        linevalues.sort((entry, t1) -> entry.getX() >= t1.getX() ? 1 : -1);

        LineDataSet linedataset = new LineDataSet(linevalues, "Lite");
        linedataset.setCircleColor(color);
        linedataset.setColor(color);
        lineData.addDataSet(linedataset);
    }

    private void setLineChartData() {
        List<Entry> linevalues = new ArrayList<>();
        linevalues.add(new Entry(20f, 0.0F));
        linevalues.add(new Entry(30f, 3.0F));
        linevalues.add(new Entry(40f, 2.0F));
        linevalues.add(new Entry(50f, 1.0F));
        linevalues.add(new Entry(60f, 8.0F));
        linevalues.add(new Entry(70f, 10.0F));
        linevalues.add(new Entry(80f, 1.0F));
        linevalues.add(new Entry(90f, 2.0F));
        linevalues.add(new Entry(100f, 5.0F));
        linevalues.add(new Entry(110f, 1.0F));
        linevalues.add(new Entry(120f, 20.0F));
        linevalues.add(new Entry(130f, 40.0F));
        linevalues.add(new Entry(140f, 50.0F));

        LineDataSet linedataset = new LineDataSet(linevalues, "Lite");
        lineData.addDataSet(linedataset);
    }
}