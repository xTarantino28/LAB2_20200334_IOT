package com.example.lab2_20200334_iot.threads;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppThreads extends Application {
    public ExecutorService executorService = Executors.newFixedThreadPool(4);
}
