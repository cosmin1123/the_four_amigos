package com.the_four_amigos.panic_helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.speech.tts.TextToSpeech;
import com.the_four_amigos.panic_helper.alerts.Speak;
import com.the_four_amigos.panic_helper.sensors.Acceleration;

public class MainActivity extends Activity {
    private SensorManager mSensorManager;
    public static boolean running;
    private static Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent accelerationService = new Intent(this, Acceleration.class);
        startService(accelerationService);


        MainActivity.context = getApplicationContext();

       // stopService(accelerationService);

    }

    public void setConfiguration(View view) {
        Intent intent = new Intent(this, ConfigurationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        running = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        running = false;
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }

}
