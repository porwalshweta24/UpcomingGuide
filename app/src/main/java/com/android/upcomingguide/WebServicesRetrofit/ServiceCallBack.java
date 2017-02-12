package com.android.upcomingguide.WebServicesRetrofit;

import retrofit.RetrofitError;


public interface ServiceCallBack
{
    public void onSuccess(int tag, String baseResponse);
    public void onFail(int tag, RetrofitError error);
}
