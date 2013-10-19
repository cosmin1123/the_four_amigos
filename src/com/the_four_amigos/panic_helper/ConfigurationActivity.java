package com.the_four_amigos.panic_helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ConfigurationActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.configuration);
    }
}