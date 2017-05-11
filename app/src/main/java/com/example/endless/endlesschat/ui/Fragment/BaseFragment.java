package com.example.endless.endlesschat.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by endless .
 */

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    public abstract int getLayoutRes();

    public void init(){

    }

    public void goTo(Class activity) {
        Intent intent = new Intent(getContext(), activity);
        startActivity(intent);
        getActivity().finish();
    }

    public void goTo(Class activity,boolean isfinish) {
        Intent intent = new Intent(getContext(), activity);
        startActivity(intent);
        if(isfinish){
            getActivity().finish();
        }
    }

}
