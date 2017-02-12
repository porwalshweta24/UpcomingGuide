package com.android.upcomingguide.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.upcomingguide.Adapter.UpcomingGuideAdapter;
import com.android.upcomingguide.Pojo.UpcomingGuides;
import com.android.upcomingguide.R;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shweta on 2/11/2017.
 */

public class ActivityMain extends AppCompatActivity{
    private static final String TAG = ActivityMain.class.getSimpleName();

    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.recycleView_cart)
    public RecyclerView  recyclerView;
    UpcomingGuideAdapter upcomingGuideAdapter;
    UpcomingGuides upcomingGuides;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        upcomingGuides = (UpcomingGuides)getIntent().getExtras().getSerializable("upcomingGuides");
        setupToolbar();
        setUpcomingGuideData();
    }
    void setUpcomingGuideData()
    {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        upcomingGuideAdapter= new UpcomingGuideAdapter(ActivityMain.this,upcomingGuides.getData());
        recyclerView.setAdapter(upcomingGuideAdapter);

    }
    void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}
