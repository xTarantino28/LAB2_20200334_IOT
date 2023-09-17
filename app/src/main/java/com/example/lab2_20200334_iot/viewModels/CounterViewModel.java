package com.example.lab2_20200334_iot.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CounterViewModel extends ViewModel {
    private final MutableLiveData<Integer> counter = new MutableLiveData<>();

    public MutableLiveData<Integer> getCounter() {
        return counter;
    }
}
