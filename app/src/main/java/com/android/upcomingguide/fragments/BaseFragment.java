package com.android.upcomingguide.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.upcomingguide.Interfaces.BackPressListener;


public abstract class BaseFragment extends Fragment implements BackPressListener {
    protected BackHandlerInterface backHandlerInterface;

    public abstract String getTagText();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!(getActivity()  instanceof BackHandlerInterface)) {
            throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
        } else {
            backHandlerInterface = (BackHandlerInterface) getActivity();
        }
    }

    public boolean onBackPressed(){
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Mark this fragment as the selected Fragment.
        backHandlerInterface.setSelectedFragment(this);
    }



    public interface BackHandlerInterface {
        public void setSelectedFragment(BaseFragment backHandledFragment);
    }


}