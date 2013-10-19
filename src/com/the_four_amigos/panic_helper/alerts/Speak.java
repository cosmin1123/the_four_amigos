package com.the_four_amigos.panic_helper.alerts;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Speak implements TextToSpeech.OnInitListener
{
    public TextToSpeech tts;

    public Speak(Context c)
    {
        tts = new TextToSpeech(c, this);
    }

    @Override
    public void onInit(int status) {
        if(status != TextToSpeech.ERROR){
            tts.setLanguage(Locale.US);
        }
    }

    public void speakWords(String textHolder){
        tts.speak(textHolder, TextToSpeech.QUEUE_FLUSH, null);
    }
}