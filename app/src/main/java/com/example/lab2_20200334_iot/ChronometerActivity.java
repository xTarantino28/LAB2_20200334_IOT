package com.example.lab2_20200334_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import com.example.lab2_20200334_iot.databinding.ActivityChronometerBinding;
import com.example.lab2_20200334_iot.databinding.ActivityCounterBinding;

public class ChronometerActivity extends AppCompatActivity {

    private ActivityChronometerBinding binding;
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChronometerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toast.makeText(ChronometerActivity.this, "Chronometer page", Toast.LENGTH_SHORT).show();


        chronometer = binding.chronometer;
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 120000) { //contara hasta 120000 y luego se detendra.
                    stopChronometer(chronometer);
                    Toast.makeText(ChronometerActivity.this, "Bing!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void startChronometer(View v) {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer(View v) {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void clearChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
    public void stopChronometer(View v) {
        chronometer.stop();
        pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
        running = false;
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
}