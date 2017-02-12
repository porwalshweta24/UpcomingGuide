package com.android.upcomingguide.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.upcomingguide.Adapter.UpcomingGuideAdapter;
import com.android.upcomingguide.Pojo.UpcomingGuides;
import com.android.upcomingguide.R;
import com.android.upcomingguide.Utility.JsonDataParser;
import com.android.upcomingguide.Utility.Utils;
import com.android.upcomingguide.Views.ActivityLogIn;
import com.android.upcomingguide.Views.ActivityMain;
import com.android.upcomingguide.WebServicesRetrofit.Api;
import com.android.upcomingguide.WebServicesRetrofit.BaseRequest;
import com.android.upcomingguide.WebServicesRetrofit.ServiceCallBack;
import com.android.upcomingguide.database.DBCartAdapter;
import com.google.gson.reflect.TypeToken;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RetrofitError;

/**
 * Created by shweta on 2/11/2017.
 */

public class UpcomingGuidesFragment extends BaseFragment implements ServiceCallBack {

    View view;
    @Bind(R.id.recycleView_cart)
    public RecyclerView recyclerView;
    UpcomingGuideAdapter upcomingGuideAdapter;
    UpcomingGuides upcomingGuides;
    Context context;

    public void setArgumentUI(Context context)
    {
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main, container, false);
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getUpcomingGuideByAPI();
//
    }
    void getUpcomingGuideByAPI()
    {
        try{
                BaseRequest baseRequest = new BaseRequest(context);
                baseRequest.setServiceCallBack(this);
                baseRequest.setRequestTag(Utils.UPCOMING_GUIDE_TAG);
                Api api = (Api) baseRequest.execute(Api.class);
                if (api != null) {
                    api.getUpcomingGuides(baseRequest.requestCallback());
                }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void setUpcomingGuideData()
    {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        //getting values from db
        DBCartAdapter dbCartAdapter = new DBCartAdapter(context);
        upcomingGuideAdapter= new UpcomingGuideAdapter(getActivity(),dbCartAdapter.getMenuListForAll());

        recyclerView.setAdapter(upcomingGuideAdapter);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

    }


    @Override
    public boolean onBackPressed() {
//        getActivity().onBackPressed();

        return super.onBackPressed();
    }

    @Override
    public String getTagText() {
        return "UPCOMING GUIDE";
    }



    @Override
    public void onSuccess(int tag, String baseResponse) {
        switch (tag) {

            case Utils.UPCOMING_GUIDE_TAG:
                try {
                    upcomingGuides =
                            JsonDataParser.getInternalParser(baseResponse, new TypeToken<UpcomingGuides>() {
                            }.getType());

                    if(upcomingGuides!=null)
                    {
                        //saving values in db
                        DBCartAdapter dbCartAdapter = new DBCartAdapter(context);
                        dbCartAdapter.createNewItem(upcomingGuides.getData());
                        setUpcomingGuideData();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                 break;
        }
    }

    @Override
    public void onFail(int tag, RetrofitError error) {
        try {
            Utils.showToast(context, "" + error);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
