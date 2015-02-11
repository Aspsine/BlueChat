package com.aspsine.bluechat.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by littlexi on 2015/2/2.
 */
public class SharedPrefsUtils {

    public static final String ROLE_BLUE = "BLUE";
    public static final String ROLE_PINK = "PINK";

    private static final String PREF_WELCOME_DONE = "pref_welcome_done";

    private static final String PREF_ROLE = "pref_role";


    public static boolean isWelcomeDone(Context context) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_WELCOME_DONE, false);
    }

    public static void markWelcomeDone(Context context) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_WELCOME_DONE, true).apply();
    }

    public static String getRole(Context context) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_ROLE, "NULL");
    }

    /**
     * ROLE_BLUE
     * ROLE_PINK
     * NULL
     */
    public static void markRole(Context context, String role) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_ROLE, role).apply();
    }
}
