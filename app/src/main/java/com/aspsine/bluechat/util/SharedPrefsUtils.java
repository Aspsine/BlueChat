package com.aspsine.bluechat.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.aspsine.bluechat.ui.activity.GuiderActivity;

/**
 * Created by littlexi on 2015/2/2.
 */
public class SharedPrefsUtils {

    private static final String PREF_WELCOME_DONE = "pref_welcome_done";


    public static boolean isWelcomeDone(Context context) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_WELCOME_DONE, false);
    }

    public static void markWelcomeDone(Context context){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_WELCOME_DONE, true).apply();
    }
}
