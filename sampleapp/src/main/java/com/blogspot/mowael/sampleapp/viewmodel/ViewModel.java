package com.blogspot.mowael.sampleapp.viewmodel;

import com.blogspot.mowael.retrofitcore.services.Service;
import com.blogspot.mowael.sampleapp.view.MainView;
import com.blogspot.mowael.sampleapp.common.RestClient;
import com.blogspot.mowael.sampleapp.pojo.Athletes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModel {
    private final MainView view;

    public ViewModel(MainView view) {
        this.view = view;
    }

    public void getAthletes() {
        Service<Athletes, RestClient> service = new Service<>();
        RestClient restClient = service.createRestClient(RestClient.class);
        service.sendAsync(restClient.getAthletes(), new Callback<Athletes>() {
            @Override
            public void onResponse(Call<Athletes> call, Response<Athletes> response) {
                //TODO do whatever you want
                view.showToast(response.body().getAthletes().get(0).getName());
            }

            @Override
            public void onFailure(Call<Athletes> call, Throwable t) {
                //TODO handle errors here
                view.showToast("error: " + t.getMessage());
            }
        });
    }
}
