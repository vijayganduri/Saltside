package com.vijayganduri.saltside.ui.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Shared Preferences Helper
 *
 * @author Vijay Ganduri
 */
public class PreferencesUtils {

    public static final String SHARED_PREFS_FILE = "com.vijayganduri.stackup.prefs";

    public static final String PREFS_APP_CURRENT_VERSION = "com.vijayganduri.stackup.prefs.app.version";

    private static SharedPreferences prefs;

    public static SharedPreferences getPreferences(Context context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences(SHARED_PREFS_FILE, ContextWrapper.MODE_PRIVATE);
        }
        return prefs;
    }

    /////////////////////
    //    GETTERS
    /////////////////////

    public static int getAppVersion(Context context) {
        return getPreferences(context).getInt(PREFS_APP_CURRENT_VERSION, 0);
    }

    ///////////////////
    //    SETTERS
    //////////////////

    public static void setAppVersion(Context context, int value) {
        final Editor editor = getPreferences(context).edit();
        editor.putInt(PREFS_APP_CURRENT_VERSION, value).commit();
    }

}