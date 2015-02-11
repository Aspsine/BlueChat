package com.aspsine.bluechat.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.aspsine.bluechat.R;
import com.aspsine.bluechat.ui.activity.GuiderActivity;
import com.aspsine.bluechat.util.SharedPrefsUtils;

/**
 * Created by sf on 2015/2/2.
 */
public class SplashFragment extends Fragment {
    public static final String TAG = SplashFragment.class.getSimpleName();
    private ImageView ivRole;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        String role = SharedPrefsUtils.getRole(getActivity());
        int resId = 0;
        if (role.equals(SharedPrefsUtils.ROLE_PINK)){
            resId = R.drawable.ic_launcher_pink;
        }else {
            resId = R.drawable.ic_launcher;
        }
        ivRole = (ImageView) view.findViewById(R.id.ivRole);
        ivRole.setBackgroundResource(resId);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.splash);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((GuiderActivity) getActivity()).intentToMain();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ivRole.setAnimation(animation);
        animation.start();
    }

}
