package com.android.upcomingguide.Views;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.android.upcomingguide.R;

/**
 * Created by shweta on 2/10/2017.
 */

public class ActivitySplash extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                	Intent browserIntent = new Intent(ActivitySplash.this, ActivityLogIn.class);
                    startActivity(browserIntent);
                    finish();

            }
        }, 1000);
    }

}
