package com.example.lab2_20200334_iot.api;

import com.example.lab2_20200334_iot.pojos.RootPojo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

    //https://randomuser.me/api/
    @GET("/api/")
    Call<RootPojo> getUserInfo();
}
