package com.android.upcomingguide.WebServicesRetrofit;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.mime.TypedInput;

public interface Api {

    @GET("/upcomingGuides")
    public void getUpcomingGuides(Callback<String> callback);
}
