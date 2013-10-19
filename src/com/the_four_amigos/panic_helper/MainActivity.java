package com.example.the_four_amigos;

import android.app.Activity;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class MyActivity extends Activity {

    private SensorManager mSensorManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Acceleration.startAcceleration(mSensorManager);

        Intent accelerationService = new Intent(this, Acceleration.class);
        startService(accelerationService);
        stopService(accelerationService);
    }

    public void stopStartedService(Intent serviceName){
        stopService(serviceName);

    }



}
