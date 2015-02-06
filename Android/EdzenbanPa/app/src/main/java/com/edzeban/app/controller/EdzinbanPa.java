package com.edzeban.app.controller;

import android.app.Activity;
import android.app.Application;
import android.view.ViewGroup;

import com.edzeban.app.R;
import com.edzeban.app.service.EdzinbanPaService;
import com.squareup.okhttp.OkHttpClient;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by rubin on 2/6/15.
 */
public class EdzinbanPa extends Application {
    public EdzinbanPaService edzinbanPaService;
    public static EdzinbanPa edzinbanPa ;
    @Override
    public void onCreate() {

        super.onCreate();

        edzinbanPa = this;
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(EdzinbanPaService.api)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(new OkHttpClient()))
                .build();
        edzinbanPaService = restAdapter.create(EdzinbanPaService.class);
    }



}
