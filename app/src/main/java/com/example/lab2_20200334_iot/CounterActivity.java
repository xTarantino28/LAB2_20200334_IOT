package com.example.lab2_20200334_iot;

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

public class CounterActivity extends AppCompatActivity {
    private ActivityCounterBinding binding;
    private int increment = 10;
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
                    for (int i = 104; i <= 226; i = i+increment) {
                        counterViewModel.getCounter().postValue(i);
                        Log.d("msg-test", "i: " + i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
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
        });

    }
}