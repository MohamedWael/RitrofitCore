package com.blogspot.mowael.sampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.blogspot.mowael.retrofitcore.RetrofitBase;
import com.blogspot.mowael.retrofitcore.services.Service;
import com.blogspot.mowael.sampleapp.pojo.Athletes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RetrofitBase.initialize(Constants.URL);

        Service<Athletes, RestClient> service = new Service<>();
        RestClient restClient = service.createRestClient(RestClient.class);
        service.sendAsync(restClient.getAthletes(), new Callback<Athletes>() {
            @Override
            public void onResponse(Call<Athletes> call, Response<Athletes> response) {
                showToast(response.body().getAthletes().get(0).getName());
            }

            @Override
            public void onFailure(Call<Athletes> call, Throwable t) {
                showToast("error: " + t.getMessage());
            }
        });


    }

    private void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
