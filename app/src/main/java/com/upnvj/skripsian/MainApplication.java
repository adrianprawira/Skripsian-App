package com.upnvj.skripsian;

import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDexApplication;

import timber.log.Timber;

public class MainApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
