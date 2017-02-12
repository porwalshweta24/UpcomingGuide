package com.android.upcomingguide.WebServicesRetrofit;

import android.content.Context;

import retrofit.RequestInterceptor;


public class SessionRequestInterceptor implements RequestInterceptor {
    private Context context;

    public  SessionRequestInterceptor(Context context){
        this.context = context;
    }
    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("Content-Type", "application/json");
        request.addHeader("accept", "application/json");



    }
}
