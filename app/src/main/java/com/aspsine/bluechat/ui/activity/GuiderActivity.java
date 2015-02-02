package com.aspsine.bluechat.ui.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.aspsine.bluechat.R;
import com.aspsine.bluechat.ui.fragment.GuiderFragment;
import com.aspsine.bluechat.ui.fragment.SplashFragment;
import com.aspsine.bluechat.util.SharedPrefsUtils;

public class GuiderActivity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guider);
        FragmentManager fm = getFragmentManager();
        if (SharedPrefsUtils.isWelcomeDone(this)) {
            splash(fm);
        } else {
            guide(fm);
        }
    }

    void guide(FragmentManager fm) {
        fm.beginTransaction().add(R.id.guiderContainer, new GuiderFragment(), GuiderFragment.TAG).commit();
    }

    void splash(FragmentManager fm) {
        fm.beginTransaction().add(R.id.guiderContainer, new SplashFragment(), SplashFragment.TAG).commit();
    }

    public void intentToMain() {
        if(!SharedPrefsUtils.isWelcomeDone(this)){
            SharedPrefsUtils.markWelcomeDone(this);
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
