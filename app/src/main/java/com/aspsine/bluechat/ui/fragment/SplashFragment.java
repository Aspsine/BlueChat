package com.aspsine.bluechat.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.bluechat.R;
import com.aspsine.bluechat.ui.activity.GuiderActivity;

/**
 * Created by sf on 2015/2/2.
 */
public class SplashFragment extends Fragment {
    public static final String TAG = SplashFragment.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((GuiderActivity)getActivity()).intentToMain();
    }

}
