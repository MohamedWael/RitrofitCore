package com.blogspot.mowael.sampleapp;

import retrofit2.http.GET;

public interface RestClient {

    @GET
    void executeRestApi();
}
