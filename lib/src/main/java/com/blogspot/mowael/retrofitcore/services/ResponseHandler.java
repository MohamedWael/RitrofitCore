package com.blogspot.mowael.retrofitcore.services;

public interface ResponseHandler<Response> {
    void onSuccess(Response response );
    void onFailure(ErrorHandler errorHandler);
}
