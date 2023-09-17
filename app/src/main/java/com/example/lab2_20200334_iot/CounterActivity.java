package com.example.lab2_20200334_iot;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.lab2_20200334_iot.databinding.ActivityCounterBinding;
import com.example.lab2_20200334_iot.databinding.ActivitySignUpBinding;
import com.example.lab2_20200334_iot.threads.AppThreads;
import com.example.lab2_20200334_iot.viewModels.CounterViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class CounterActivity extends AppCompatActivity {
    private ActivityCounterBinding binding;
    private int increment = 10;
     int i = 104;
    private boolean firstClick = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCounterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toast.makeText(CounterActivity.this, "Counter page", Toast.LENGTH_SHORT).show();

        AppThreads application = (AppThreads) getApplication();
        ExecutorService executorService = application.executorService;

        CounterViewModel counterViewModel = new ViewModelProvider(CounterActivity.this).get(CounterViewModel.class);
        counterViewModel.getCounter().observe(this, counter -> {
            binding.counterTextView.setText(String.valueOf(counter));
        });



        binding.Start.setOnClickListener(view -> {
            if ( firstClick ) {
                executorService.execute(() -> {
                    for (i = 104; i <= 226; i = i+increment) {
                        counterViewModel.getCounter().postValue(i);
                        Log.d("msg-test", "i: " + i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (  (226-(i+increment) <= 0)) {
                            counterViewModel.getCounter().postValue(226);
                            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                            // Vibrate for 500 milliseconds
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                //deprecated in API 26
                                v.vibrate(500);
                            }
                        }
                    }
                });
                firstClick = false;

            } else {
                increment = (int) (increment + Math.round(0.5*increment));
            }

        });

        binding.Reset.setOnClickListener(view -> {
            counterViewModel.getCounter().postValue(104);
            firstClick = true;
            increment = 10;
            i = 104;
        });


    }
}