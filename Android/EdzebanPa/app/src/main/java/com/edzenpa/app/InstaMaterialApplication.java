package com.edzenpa.app;
/**
 * Created by gidis on 2/3/15.
 */
import android.app.Application;

import timber.log.Timber;

public class InstaMaterialApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
