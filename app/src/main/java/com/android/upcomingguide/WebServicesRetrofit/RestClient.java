package com.android.upcomingguide.WebServicesRetrofit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.upcomingguide.Utility.Utils;
import com.squareup.okhttp.OkHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class RestClient {

    private BaseRequest baseRequest;
    private ProgressDialog pDialog = null;
    private Context context;

    public RestClient(Context context, BaseRequest _baseRequest) {
        this.baseRequest = _baseRequest;
        this.context = context;
    }

    public Object execute(Class classes) {
        if (baseRequest.isProgressShow())
            showProgressDialog(context);
        return setUpRestClient(classes);
    }

    private Object setUpRestClient(Class apiClasses) {

        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Utils.BASE_URL)
                .setRequestInterceptor(new SessionRequestInterceptor(context))
                .setClient(new OkClient(okHttpClient));

        builder.setConverter(new StringConverter());
        RestAdapter restAdapter = builder.build();
        return restAdapter.create(apiClasses);

    }

    public Callback<String> callback = new Callback<String>() {

        @Override
        public void success(String o, Response response) {
            if (o != null && !o.isEmpty()) {
                baseRequest.getServiceCallBack().onSuccess(baseRequest.getRequestTag(), o);
            }
            dismissProgressDialog();
        }

        @Override
        public void failure(RetrofitError error) {

            baseRequest.getServiceCallBack().onFail(baseRequest.getRequestTag(),error);
            dismissProgressDialog();
        }
    };

    public void showProgressDialog(Context context) {
        try {
            if (((Activity) context).isFinishing()) {
                return;
            }
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgressDialog() {
        try {
            if (pDialog != null)
                pDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class StringConverter implements Converter {

        @Override
        public Object fromBody(TypedInput typedInput, Type type) throws ConversionException {
            String text = null;
            try {

                text = fromStream(typedInput.in());
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
            return text;
        }

        @Override
        public TypedOutput toBody(Object o) {

            return null;
        }

        private String fromStream(InputStream in) throws IOException {

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String newLine = System.getProperty("line.separator");
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
                out.append(newLine);
            }
            return out.toString();
        }
    }

}
