package com.the_four_amigos.panic_helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.the_four_amigos.panic_helper.alerts.Speak;
import com.the_four_amigos.panic_helper.sensors.Acceleration;

import java.util.ArrayList;

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

    public void voiceRecognitionStart() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {
            startActivityForResult(i, 1);
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Log.d("Main", thingsYouSaid.get(0));
        }
    }

  

}
