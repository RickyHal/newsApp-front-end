package com.example.win10.personality_newsapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Timer;
import java.util.TimerTask;

public class AnimationActivity extends Activity {



    private ImageView imageView;
    private AnimationDrawable animationDrawable;
    private int recLen = 5;
    private TextView txtView;
    Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        imageView=findViewById(R.id.animation_show);
        animationDrawable=(AnimationDrawable)imageView.getBackground();
        txtView = (TextView)findViewById(R.id.txttime);

        timer.schedule(task, 1000, 1000);

        animationDrawable.start();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent();
                intent.setClass(AnimationActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },5500);

    }
    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    recLen--;
                    txtView.setText(""+recLen);
                    if(recLen < 0){
                        timer.cancel();
                        txtView.setVisibility(View.GONE);
                    }
                }
            });
        }
    };
    protected void onDestroy() {
        super.onDestroy();
        if (null != animationDrawable && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }
}
