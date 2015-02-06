package com.edzeban.app.controller;

import android.app.Application;

import com.edzeban.app.service.EdzinbanPaService;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by rubin on 2/6/15.
 */
public class EdzinbanPa extends Application {
    public static EdzinbanPaService edzinbanPa;
    @Override
    public void onCreate() {

        super.onCreate();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(EdzinbanPaService.api)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(new OkHttpClient()))
                .build();
        edzinbanPa = restAdapter.create(EdzinbanPaService.class);
    }

}
