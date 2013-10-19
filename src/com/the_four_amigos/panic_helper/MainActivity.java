package com.the_four_amigos.panic_helper;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import com.the_four_amigos.panic_helper.sensors.Acceleration;

public class MainActivity extends Activity {

    private SensorManager mSensorManager;
    public static boolean running;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent accelerationService = new Intent(this, Acceleration.class);
        startService(accelerationService);


       // stopService(accelerationService);
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

}
