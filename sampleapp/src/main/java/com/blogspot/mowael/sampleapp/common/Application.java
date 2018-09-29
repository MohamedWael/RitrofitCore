package com.blogspot.mowael.sampleapp.common;

import com.blogspot.mowael.retrofitcore.RetrofitBase;
import com.blogspot.mowael.sampleapp.BuildConfig;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitBase.initialize(Constants.URL, BuildConfig.DEBUG);
    }
}
