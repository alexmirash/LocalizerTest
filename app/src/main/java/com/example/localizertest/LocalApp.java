package com.example.localizertest;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * @author Mirash
 */
public class LocalApp extends Application {
    private static LocalApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static LocalApp getInstance() {
        return app;
    }

    @Override
    protected void attachBaseContext(Context base) {
        Log.d(LocaleHelper.TAG, "APP: attachBaseContext");
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Log.d(LocaleHelper.TAG, "APP: onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
        LocaleHelper.onAttach(this);
    }
}
