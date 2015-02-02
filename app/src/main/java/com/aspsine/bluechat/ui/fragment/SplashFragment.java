package com.aspsine.bluechat.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by sf on 2015/2/2.
 */
public class SplashFragment extends Fragment{
    public static final String TAG = SplashFragment.class.getSimpleName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface OnSplashCompleteCallBack{
        public void onSplashComplete();
    }
}
