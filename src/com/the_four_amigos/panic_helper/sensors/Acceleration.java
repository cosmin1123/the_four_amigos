package com.the_four_amigos.panic_helper.sensors;

import android.R;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import com.the_four_amigos.panic_helper.Configurations;
import com.the_four_amigos.panic_helper.DangerAlarm;
import com.the_four_amigos.panic_helper.MainActivity;
import com.the_four_amigos.panic_helper.alerts.Speak;

import java.util.List;

public class Acceleration extends Service implements SensorEventListener{
    private Speak talk = new Speak(MainActivity.getAppContext());
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private boolean applicationRunning = false;
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
        stopForeground(true);
    }
    public void onStart(Intent intent, int startId)
    {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        lastUpdate = System.currentTimeMillis();

        startForeground(1, new Notification());
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

            if( (mAccel / 9.81) > Configurations.alarmGravity){

                talk.speakWords("Panic alarm will start in 30 seconds, please press on the screen or say stop to cancel.");

                //MainActivity

               if(!MainActivity.running){
                   startMainActivity();
                }

                alertMessage();

            }


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

    private void startMainActivity(){
        Intent dialogIntent = new Intent(getBaseContext(), MainActivity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(dialogIntent);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(dialogIntent);
        MainActivity.running = true;
    }

    private void alertMessage(){
        //TextToSpeech("Stop it!!");
        Intent dialogIntent = new Intent(MainActivity.getAppContext(), DangerAlarm.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(dialogIntent);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(dialogIntent);
        MainActivity.running = true;
    }

    protected Boolean isActivityRunning(Class activityClass)
    {
        ActivityManager activityManager = (ActivityManager) getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
                return true;
        }

        return false;
    }

}
