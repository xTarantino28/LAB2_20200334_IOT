package com.example.lab2_20200334_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lab2_20200334_iot.api.UserService;
import com.example.lab2_20200334_iot.databinding.ActivityMenuBinding;
import com.example.lab2_20200334_iot.databinding.ActivitySignUpBinding;
import com.example.lab2_20200334_iot.pojos.Result;
import com.example.lab2_20200334_iot.pojos.RootPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding binding;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toast.makeText(MenuActivity.this, "Menu page", Toast.LENGTH_SHORT).show();
        userService = new Retrofit.Builder()
                .baseUrl("https://randomuser.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserService.class);

        fetchWebServiceUserData();


        binding.chronometerButtonOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ChronometerActivity.class);
                startActivity(intent);
            }
        });

        binding.counterButtonOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, CounterActivity.class);
                startActivity(intent);
            }
        });

    }


    public void fetchWebServiceUserData() {
        if(tengoInternet()){
            userService.getUserInfo().enqueue(new Callback<RootPojo>() {
                @Override
                public void onResponse(Call<RootPojo> call, Response<RootPojo> response) {
                    //aca estoy en el UI Thread
                    if(response.isSuccessful()){
                        RootPojo rootPojo = response.body();
                       if (rootPojo != null) {
                          Result result = rootPojo.getResults().get(0);
                          binding.userNameTextView.setText(result.getLogin().getUsername());
                          String completeName = result.getName().getFirst() + " " + result.getName().getLast();
                          binding.completeNameTextView.setText(completeName);
                          String urlAvatar = result.getPicture().getThumbnail();
                          Glide.with(MenuActivity.this).load(urlAvatar).into(binding.avatarImageView);
                       }

                    }
                }

                @Override
                public void onFailure(Call<RootPojo> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(MenuActivity.this, "Internet connection required to display user information", Toast.LENGTH_SHORT).show();
        }
    }



    public boolean tengoInternet() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        boolean tieneInternet = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        Log.d("msg-test", "Internet: " + tieneInternet);

        return tieneInternet;
    }
}