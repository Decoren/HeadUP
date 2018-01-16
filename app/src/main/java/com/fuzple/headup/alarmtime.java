package com.fuzple.headup;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by user on 2018-01-12.
 */

public class alarmtime extends AppCompatActivity {

    Vibrator vb;
    ImageView iv;
    Button btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmtimer);

        iv = (ImageView)findViewById(R.id.timer_i);
        btn = (Button) findViewById(R.id.timer_b);
        vb = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        vb.vibrate(1000);
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(this).load(R.drawable.alarmtimer).apply(options).into(iv);
    }
}
