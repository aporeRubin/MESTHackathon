package com.edzeban.app;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;


import com.edzeban.app.fragment.AboutFragment;
import com.edzeban.app.fragment.MainFragment;
import com.edzeban.app.util.ProgressHUB;


import it.neokree.googlenavigationdrawer.GAccount;
import it.neokree.googlenavigationdrawer.GAccountListener;
import it.neokree.googlenavigationdrawer.GSection;
import it.neokree.googlenavigationdrawer.GoogleNavigationDrawer;

public class MainActivity extends GoogleNavigationDrawer implements GAccountListener {
    private static String TAG = "MainActivity" ;
    GAccount account;
    GSection settingsSection, aboutSection, homeSection;

    @Override
    public void init(Bundle savedInstanceState) {
        account = new GAccount("Edzeban Pa", "Eat Healthy!!", new ColorDrawable(Color.parseColor("#4caf50")), this.getResources().getDrawable(R.drawable.bofroat));
        this.addAccount(account);
        this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4caf50")));
        this.setAccountListener(this);
       // settingsSection = this.newSection("Settings",this.getResources().getDrawable(R.drawable.blank_1),new SettingsFragment()).setSectionColor(Color.parseColor("#4caf50"));
        aboutSection=this.newSection("About", this.getResources().getDrawable(R.drawable.blank_2), new AboutFragment()).setSectionColor(Color.parseColor("#4caf50"));
      homeSection=this.newSection("Home", this.getResources().getDrawable(R.drawable.blank_2), new MainFragment()).setSectionColor(Color.parseColor("#4caf50"));
        this.addSection(homeSection);
      //  this.addBottomSection(settingsSection);
        this.addBottomSection(aboutSection);


        ProgressHUB.show(this,"loading . . ", true, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.e(TAG, "Preogress cancelled");
            }
        });

    }


    @Override
    public void onAccountOpening(GAccount account) {
        // open account activity or do what you want
    }
}