package com.blogspot.mowael.sampleapp.common;

import com.blogspot.mowael.sampleapp.pojo.Athletes;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestClient {

    @GET
    Call<Athletes> getAthletes();
}
