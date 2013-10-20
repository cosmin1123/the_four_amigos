package com.the_four_amigos.panic_helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.speech.tts.TextToSpeech;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import com.the_four_amigos.panic_helper.alerts.Speak;
import com.the_four_amigos.panic_helper.sensors.*;

public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
    private SensorManager mSensorManager;
    public static boolean running;
    private static Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Switch s = (Switch) findViewById(R.id.Switch);
        if(s != null) s.setOnCheckedChangeListener(this);

        //Intent accelerationService = new Intent(this, Acceleration.class);
        //startService(accelerationService);

        Intent voice = new Intent(this, SpeechService.class);
        startService(voice);


        MainActivity.context = getApplicationContext();

       // stopService(accelerationService);

    }

    public void setConfiguration(View view) {
        Intent intent = new Intent(this, ConfigurationActivity.class);
        startActivity(intent);
    }

    public void onHelp(View view) {
        Toast.makeText(this, "Help",Toast.LENGTH_SHORT).show();
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, "Monitored switch is " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
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
