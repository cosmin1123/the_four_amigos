package com.the_four_amigos.panic_helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class ConfigurationActivity extends Activity {

    private   ArrayAdapter<String> spinnerAdapter;
    private  AlertDialog.Builder alert;
    private Spinner spinner;
    private final static float ACCELERATION = 9.8f;
    private final static float MPS_TO_MKPH = 3.6f;
    public SeekBar gravitySeekBar;
    public TextView accelerationTextView;
    public TextView speedTextView;

    public float getSpeedFromGravity (float gravity) {
        return (float)(Math.sqrt(2 * gravity * ACCELERATION)) * MPS_TO_MKPH;
    }

    public void setGravityAndSpeed(float progress) {
        accelerationTextView.setText(Float.toString(progress + 1));
        speedTextView.setText(String.format("%.1f", getSpeedFromGravity(progress + 1)));
    }

    public void setConfigurationSliders() {

    }

    public void loadCurrentSettings() {
        setGravityAndSpeed(Configurations.alarmGravity);

    }

    private void showDialog(){
        alert = new AlertDialog.Builder(this);

        alert.setTitle("Phone Number Input");
        alert.setMessage("Please add a new Contact");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                spinnerAdapter.add(value);
                spinnerAdapter.notifyDataSetChanged();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }
    private void initPhoneSpinnerListener(){

        spinner = (Spinner)findViewById(R.id.phoneListSpinner);
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        ((ImageButton) findViewById(R.id.addPhoneNumber)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        ((ImageButton) findViewById(R.id.deletePhoneNumber)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerAdapter.remove((String) spinner.getSelectedItem());
            }
        });
    }

    public ArrayList<String> getPhoneList(){
        ArrayList<String> phoneList = new ArrayList<String>();
        for (int i = 0; i < spinnerAdapter.getCount(); i++){
            phoneList.add(spinnerAdapter.getItem(i));
        }
        return phoneList;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.configuration);

        initPhoneSpinnerListener();

        gravitySeekBar = (SeekBar) findViewById(R.id.seekBar);
        accelerationTextView = (TextView) findViewById(R.id.textView9);
        speedTextView  = (TextView) findViewById(R.id.textView11);

        gravitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar gravitySeekBar, int progress, boolean fromUser) {
                setGravityAndSpeed(progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


    }

}