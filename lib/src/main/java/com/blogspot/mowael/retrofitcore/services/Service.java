package com.blogspot.mowael.retrofitcore.services;


import com.blogspot.mowael.retrofitcore.RetrofitBase;
import com.blogspot.mowael.retrofitcore.utils.Logger;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by moham on 10/6/2017.
 */

public class Service<Response, RestClient> implements Callback<Response> {

    protected static final String MULTI_PART_FORM_DATA = "multipart/form-data";
    private Callback<Response> callback;

    /**
     * @param bodyMap body of the request
     * @return HashMap of String and RequestBody for the PartMap
     */
    public HashMap<String, RequestBody> createRequestBodyMap(HashMap<String, String> bodyMap) {
        HashMap<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : bodyMap.keySet()) {
            requestBodyMap.put(key, createRequestBody(bodyMap.get(key)));
        }
        return requestBodyMap;
    }

    /**
     * @param content the content of the request
     * @return RequestBody with multipart/form-data as content type
     */
    public RequestBody createRequestBody(String content) {
        return RequestBody.create(MediaType.parse(MULTI_PART_FORM_DATA), content);
    }

    /**
     * @param file the content of the request
     * @return RequestBody with multipart/form-data as content type
     */
    public RequestBody createRequestBody(File file) {
        return RequestBody.create(MediaType.parse(MULTI_PART_FORM_DATA), file);
    }

    public MultipartBody.Part createMultiPart(String name, File file) {
        RequestBody requestBody = createRequestBody(file);
        return MultipartBody.Part.createFormData(name, file.getName(), requestBody);
    }


    public RestClient createRestClient(Class<RestClient> tClass) {
        return RetrofitBase.getInstance().createClient(tClass);
    }

    public void sendAsync(Call<Response> call, Callback<Response> callback) {
        this.callback = callback;
        Logger.d("request", call.request().url().toString());
        Logger.d("body", call.request().body() != null ? call.request().body().toString() : "no body");
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
        Logger.d("onResponse", response.toString());
        Logger.d("onResponseMessage", response.message());
        Logger.d("onResponseCode", response.code() + "");
        Logger.d("onResponseErrorBody", response.errorBody() != null ? response.errorBody().toString() : "no Error");
        if (response.body() != null) {
            Gson gson = new Gson();
            String res = gson.toJson(response.body());
            Logger.d("response", res);
        } else Logger.d("response", "null body");
        if (callback != null) callback.onResponse(call, response);
    }

    @Override
    public void onFailure(Call<Response> call, Throwable t) {
        Logger.e("onFailureMSG", t.getMessage());
        if (callback != null) callback.onFailure(call, t);
        t.printStackTrace();
    }


    public void onStopService() {
        callback = null;
    }

}
