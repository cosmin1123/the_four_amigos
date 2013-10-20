package com.the_four_amigos.panic_helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;
import com.the_four_amigos.panic_helper.alerts.Speak;
import com.the_four_amigos.panic_helper.listener.VoiceCommandService;
import com.the_four_amigos.panic_helper.sensors.Acceleration;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private SensorManager mSensorManager;
    public static boolean running;
    private static Context context;
    static VoiceCommandService voiceCommandService;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent accelerationService = new Intent(this, Acceleration.class);
        startService(accelerationService);

     //   Intent voiceService = new Intent(this, VoiceCommandService.class);
     //   startService(voiceService);

        MainActivity.context = getApplicationContext();

      //  voiceRecognitionStart();
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

    public static void init(Context context)
    {
        voiceCommandService = new VoiceCommandService();

    }

    public static void startContinuousListening()
    {
        Intent service = new Intent(  MainActivity.getAppContext(), VoiceCommandService.class);
        MainActivity.getAppContext().startService(service);

        Message msg = new Message();
        msg.what = 1234;

        try
        {
            voiceCommandService.mServerMessenger.send(msg);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

    }

}