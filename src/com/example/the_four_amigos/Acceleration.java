package com.example.the_four_amigos;

import java.util.List;
import java.util.Locale;

import android.*;
import android.R;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class Acceleration extends Service implements SensorEventListener{
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    boolean flag=false;
    public static final String TAG = "Acceleration";
    private long lastUpdate;
    SensorManager sensorManager;
    int count=0;
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
    public void onCreate()
    {
        flag=true;
        Log.d(TAG, "onCreate");
        super.onCreate();

    }
    public void onDestroy() {

        flag=false;
        Log.d(TAG, "onDestroy");
        super.onDestroy();

    }
    public void onStart(Intent intent, int startId)
    {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        lastUpdate = System.currentTimeMillis();

        startForeground(1, new Notification());
       // setInForeground();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }
    private void getAccelerometer(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float[] values = event.values;
            // Movement
            float x = values[0];
            float y = values[1];
            float z = values[2];

            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            mAccel = Math.abs(mAccel);

            Log.d(TAG, mAccel + "");


        }
    }
    public void onSensorChanged(SensorEvent event) {

        getAccelerometer(event);


    }
    protected void onResume() {
        //super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause()
    {
        // unregister listener
       // sensorManager.unregisterListener(this);



    }

    private void setInForeground(){



        Notification note=new Notification(R.drawable.stat_notify_chat,
                "Panic Helper",
                System.currentTimeMillis());
        Intent i = new Intent(this, Acceleration.class);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pi=PendingIntent.getActivity(this, 0,
                i, 0);

        note.setLatestEventInfo(this, "Panic Helper",
                "Running",
                pi);
        note.flags|=Notification.FLAG_NO_CLEAR;

        startForeground(1337, note);

    }


    public void stopAcceleration(){
        stopForeground(true);

    }
}
