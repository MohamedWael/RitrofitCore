package com.blogspot.mowael.sampleapp.common;

import com.blogspot.mowael.retrofitcore.RetrofitBase;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitBase.initialize(Constants.URL);
    }
}
