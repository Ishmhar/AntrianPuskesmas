package com.example.antrianpuskesmas;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import com.example.antrianpuskesmas.data.model.User;

import java.util.TimeZone;

public class SmartQueueApp extends Application {
    public static final String PREF_USER_ID = "USER_ID";
    public static final String PREF_USERNAME = "USERNAME";
    public static final String PREF_EMAIL = "EMAIL";
    public static final String PREF_FIRST_NAME = "FIRST_NAME";
    public static final String PREF_LAST_NAME = "LAST_NAME";
    public static final String PREF_TOKEN = "TOKEN";
    public static final String PREF_PASSWORD = "PASSWORD";


    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        SmartQueueApp.context = getApplicationContext();
    }

    public static Context getContext() {
        return SmartQueueApp.context;
    }

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+07"));
    }

    public String getVersion() {
        String version = null;
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static SmartQueueApp getApplication(Activity activity) {
        return (SmartQueueApp) activity.getApplication();
    }

    public static SharedPreferences getSharedPreference() {
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public void setLoginPref(User userInfo,  String token, String password) {
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putLong(SmartQueueApp.PREF_USER_ID, userInfo.getId())
                .putString(SmartQueueApp.PREF_FIRST_NAME, userInfo.getFirstname())
                .putString(SmartQueueApp.PREF_LAST_NAME, userInfo.getLastname())
                .putString(SmartQueueApp.PREF_USERNAME, userInfo.getUsername())
                .putString(SmartQueueApp.PREF_EMAIL, userInfo.getEmail())
                .putString(SmartQueueApp.PREF_TOKEN, token)
                .putString(SmartQueueApp.PREF_PASSWORD, password)
                .apply();
    }

    public long getUserIdentity() {
        return getSharedPreference().getLong(PREF_USER_ID, 0L);
    }

    public String getFirstName() {
        return getSharedPreference().getString(PREF_FIRST_NAME, "");
    }

    public String getLastName() {
        return getSharedPreference().getString(PREF_LAST_NAME, "");
    }

    public String getUsername() {
        return getSharedPreference().getString(PREF_USERNAME, "");
    }

    public String getEmail() {
        return getSharedPreference().getString(PREF_EMAIL, "");
    }

    public String getToken() {
        return getSharedPreference().getString(PREF_TOKEN, "");
    }

    public String getPassword() {
        return getSharedPreference().getString(PREF_PASSWORD, "");
    }

    public void setToken(String token) {
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putString(SmartQueueApp.PREF_TOKEN, token)
                .apply();
    }

    public void clearTokenPreferences() {
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putLong(PREF_USER_ID, 0).putString(PREF_USERNAME, null)
                .putString(PREF_TOKEN, null)
                .apply();
    }
}
