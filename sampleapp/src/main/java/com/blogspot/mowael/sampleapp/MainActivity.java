package com.blogspot.mowael.sampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blogspot.mowael.retrofitcore.RetrofitBase;
import com.blogspot.mowael.retrofitcore.services.Service;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RetrofitBase.initialize("www.google.com");

        Service<String, RestClient> service = new Service<>();
        RestClient restClient = service.createRestClient(RestClient.class);
        restClient.executeRestApi();
    }
}
