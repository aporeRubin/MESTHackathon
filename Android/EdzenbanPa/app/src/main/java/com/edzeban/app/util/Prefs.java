package com.edzeban.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by frosty on 2/7/2015.
 */
public class Prefs {
    private final SharedPreferences mSp;

    public Prefs(Context ctx) {
        mSp = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public String getDiabetic() {
        return mSp.getBoolean("pref_diabetic", false) ? "1" : "0";
    }
    public String getHypertensive() {
        return mSp.getBoolean("pref_hypertensive", false) ? "1" : "0";
    }
    public String getLosingWeight() {
        return mSp.getBoolean("losing_weight", false) ? "1" : "0";
    }
    public String getRecuperating() {
        return mSp.getBoolean("pref_recuperating", false) ? "1" : "0";
    }
    public String getLactoseIntolerant() {
        return mSp.getBoolean("pref_lactose_intolerant", false) ? "1" : "0";
    }
    public String getActivityLevel() {
        return mSp.getString("pref_activity_level", "moderate");
    }

}
