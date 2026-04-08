package com.example.sensorapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer, light, proximity;
    private TextView tvAccelerometer, tvLight, tvProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAccelerometer = findViewById(R.id.tv_accelerometer);
        tvLight = findViewById(R.id.tv_light);
        tvProximity = findViewById(R.id.tv_proximity);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }

        if (accelerometer == null) tvAccelerometer.setText("Accelerometer not available");
        if (light == null) tvLight.setText("Light sensor not available");
        if (proximity == null) tvProximity.setText("Proximity sensor not available");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {
            if (accelerometer != null) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            }
            if (light != null) {
                sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
            }
            if (proximity != null) {
                sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            tvAccelerometer.setText(String.format("Accelerometer:\nX: %.2f\nY: %.2f\nZ: %.2f", x, y, z));
        } else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lightValue = event.values[0];
            tvLight.setText(String.format("Light: %.2f lx", lightValue));
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float proximityValue = event.values[0];
            tvProximity.setText(String.format("Proximity: %.2f cm", proximityValue));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this application
    }
}
