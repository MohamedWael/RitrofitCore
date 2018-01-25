package com.blogspot.mowael.retrofitcore.clients;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by moham on 10/10/2017.
 */

public interface Client<Response> {

    @GET
    Call<Response> executeGet(String url);

    @POST
    Call<Response> executePost(String url);

    @POST
    Call<Response> executePost(String url, @Body Object body);
}
