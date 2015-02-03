package com.aspsine.bluechat.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.aspsine.bluechat.R;
import com.aspsine.bluechat.ui.activity.GuiderActivity;

/**
 * Created by sf on 2015/2/2.
 */
public class GuiderFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = GuiderFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guider, container, false);

        ImageView blueAndroid = (ImageView) view.findViewById(R.id.ivAndroidBlue);

        ImageView pinkAndroid = (ImageView) view.findViewById(R.id.ivAndroidPink);

        blueAndroid.setOnClickListener(this);

        pinkAndroid.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        String whichOne;
        switch (v.getId()) {
            case R.id.ivAndroidBlue:
                whichOne = "Awesome blue.";
                break;

            case R.id.ivAndroidPink:
                whichOne = "Candy Pink.";
                break;

            default:
                return;
        }

        Toast.makeText(getActivity(), "Your role is " + whichOne, Toast.LENGTH_LONG).show();
        ((GuiderActivity) getActivity()).intentToMain();
    }

}
