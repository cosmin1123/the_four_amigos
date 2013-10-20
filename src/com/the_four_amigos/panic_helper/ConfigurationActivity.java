package com.the_four_amigos.panic_helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.*;

import java.io.IOException;
import java.util.ArrayList;

public class ConfigurationActivity extends Activity {

    private final static float ACCELERATION = 9.8f;
    private final static float MPS_TO_MKPH = 3.6f;
    private int index = 0;

    private  ArrayAdapter<String> spinnerAdapter;
    private  AlertDialog.Builder alert;

    private Spinner spinner;
    private SeekBar gravitySeekBar;
    private Button saveButton, cancelButton;

    private TextView accelerationTextView;
    private TextView speedTextView;
    private EditText mySmsText;

    private Switch[] switches;

    public float getSpeedFromGravity (float gravity) {
        return (float)(Math.sqrt(2 * gravity * ACCELERATION)) * MPS_TO_MKPH;
    }

    public void setGravityAndSpeed(float progress) {
        accelerationTextView.setText(Float.toString(progress + 1));
        speedTextView.setText(String.format("%.1f", getSpeedFromGravity(progress + 1)));
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

    public String[] getPhoneList(){
        String[] phoneList = new String[spinnerAdapter.getCount()];
        for (int i = 0; i < spinnerAdapter.getCount(); i++){
            phoneList[i] = spinnerAdapter.getItem(i);
        }
        return phoneList;
    }

    private void populateSpinnerFromList() {
        for(int i = 0; i < Configurations.myPhoneNumbers.length; i++) {
            spinnerAdapter.add(Configurations.myPhoneNumbers[i]);
            spinnerAdapter.notifyDataSetChanged();
        }
    }

    private void initPhoneSpinnerListener(){

        spinner = (Spinner)findViewById(R.id.phoneListSpinner);
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        populateSpinnerFromList(); //from settings file

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

    private void initPhoneConfigurations() throws IOException {

        gravitySeekBar = (SeekBar) findViewById(R.id.seekBar);
        accelerationTextView = (TextView) findViewById(R.id.textView9);
        speedTextView  = (TextView) findViewById(R.id.textView11);
        mySmsText = (EditText) findViewById(R.id.editText);

        switches = new Switch[9];
        switches[0] = (Switch) findViewById(R.id.Switch1);
        switches[1] = (Switch) findViewById(R.id.Switch2);
        switches[2] = (Switch) findViewById(R.id.Switch3);
        switches[3] = (Switch) findViewById(R.id.Switch4);
        switches[4] = (Switch) findViewById(R.id.Switch5);
        switches[5] = (Switch) findViewById(R.id.Switch6);
        switches[6] = (Switch) findViewById(R.id.Switch7);
        switches[7] = (Switch) findViewById(R.id.Switch8);
        switches[8] = (Switch) findViewById(R.id.Switch9);

        // Loading saved settings:
        setGravityAndSpeed(Configurations.alarmGravity - 1);
        gravitySeekBar.setProgress((int)Configurations.alarmGravity - 1);
        for (index = 0; index < switches.length; index++) {
            switches[index].setChecked(Configurations.myConfiguration[index]);
        }
        mySmsText.setText(Configurations.mySms);

        // Some listeners:
        gravitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar gravitySeekBar, int progress, boolean fromUser) {
                setGravityAndSpeed(progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.configuration);

        saveButton = (Button) findViewById(R.id.button);
        cancelButton = (Button) findViewById(R.id.button1);

        try {
            Configurations.loadConfigFile();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("********** Dim lista tel: " + Configurations.myPhoneNumbers.length);
        initPhoneSpinnerListener();

        try {
            initPhoneConfigurations();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Save my settings:
                Configurations.alarmGravity = Float.parseFloat(accelerationTextView.getText().toString());  //acceleration
                for (index = 0; index < switches.length; index++) { //configuration
                    Configurations.myConfiguration[index] = switches[index].isChecked();
                }

                Configurations.myPhoneNumbers = new String[spinnerAdapter.getCount()];
                for (int i = 0; i < spinnerAdapter.getCount(); i++){ //phone numbers
                    Configurations.myPhoneNumbers[i] = spinnerAdapter.getItem(i);
                }

                Configurations.mySms = mySmsText.getText().toString(); //sms

                try {
                    Configurations.saveConfigFile(Configurations.alarmGravity, Configurations.myConfiguration, Configurations.mySms, Configurations.myPhoneNumbers);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}