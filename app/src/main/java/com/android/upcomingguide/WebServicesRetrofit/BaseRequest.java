package com.android.upcomingguide.WebServicesRetrofit;

import android.content.Context;

import com.android.upcomingguide.Network.ConnectionDetector;

import retrofit.Callback;

public class BaseRequest {

    private int requestTag;
    private boolean progressShow = true;
    private ServiceCallBack serviceCallBack;
    private RestClient restClient;
    private Context context;
    private Callback<Object> callback;
    private ConnectionDetector cd;
    private boolean isInternetPresent;
    public BaseRequest(Context context){
        this.context = context;
        setProgressShow(progressShow);
        cd = new ConnectionDetector(context);
        isInternetPresent = cd.isConnectingToInternet();
    }

    public Object execute(Class classes){
        if(isInternetPresent){

            restClient=  new RestClient(context,this);
            return restClient.execute(classes);
        }else{
//            Utility.showToast(context, context.getString(R.string.internet_connection));
            return null;
        }
    }

    public Callback<String> requestCallback(){

        return restClient.callback;
    }
    public Callback<Object> getCallback() {
        return callback;
    }

    public void setCallback(Callback<Object> callback) {
        this.callback = callback;
    }

    public RestClient getRestClient() {
        return restClient;
    }

    public void setRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public ServiceCallBack getServiceCallBack() {
        return serviceCallBack;
    }

    public void setServiceCallBack(ServiceCallBack serviceCallBack) {
        this.serviceCallBack = serviceCallBack;
    }

    public int getRequestTag() {
        return requestTag;
    }

    public void setRequestTag(int requestType) {
        this.requestTag = requestType;
    }

    public boolean isProgressShow() {
        return progressShow;
    }

    public void setProgressShow(boolean progressShow) {
        this.progressShow = progressShow;
    }

}
