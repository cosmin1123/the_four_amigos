package com.the_four_amigos.panic_helper;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.the_four_amigos.panic_helper.sensors.Acceleration;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: Cataaa
 * Date: 20.10.2013
 * Time: 02:24
 * To change this template use File | Settings | File Templates.
 */
public class DangerAlarm extends Activity {
    private static final int DELAY = 100;
    private TextView secondsView,titleView;
    private boolean colorChanged;
    private int secondsRemaining;
    private LinearLayout layout;
    private Handler handler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danger_layout);
        layout = (LinearLayout) findViewById(R.id.dangerLayout);
        secondsView = (TextView) findViewById(R.id.seconds_textView);
        titleView = (TextView) findViewById(R.id.danger_title);
        colorChanged = false;
        secondsRemaining = 30;

        layout.setBackgroundResource(R.drawable.background_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.start();

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                secondsView.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                secondsView.setText("done!");
            }
        }.start();

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
