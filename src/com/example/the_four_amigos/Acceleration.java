package com.example.the_four_amigos;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: cosmin
 * Date: 10/19/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Acceleration  {

    static SensorManager mSensorManager;
    static float mAccel = 0.00f; // acceleration apart from gravity
    static float mAccelCurrent = SensorManager.GRAVITY_EARTH; // current acceleration including gravity
    static float mAccelLast = SensorManager.GRAVITY_EARTH; // last acceleration including gravity

    private static final SensorEventListener mSensorListener = new SensorEventListener() {
         @Override
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            mAccel = Math.abs(mAccel);

            Log.i("info", "accel M: " + mAccel );

        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public static void startAcceleration(SensorManager mSensorManager){

        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }
}
