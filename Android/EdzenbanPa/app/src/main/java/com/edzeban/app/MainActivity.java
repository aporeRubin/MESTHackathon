package com.edzeban.app;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


import com.edzeban.app.fragment.AboutFragment;
import com.edzeban.app.fragment.MainFragment;


import it.neokree.googlenavigationdrawer.GAccount;
import it.neokree.googlenavigationdrawer.GAccountListener;
import it.neokree.googlenavigationdrawer.GSection;
import it.neokree.googlenavigationdrawer.GoogleNavigationDrawer;

public class MainActivity extends GoogleNavigationDrawer implements GAccountListener {

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
    }


    @Override
    public void onAccountOpening(GAccount account) {
        // open account activity or do what you want
    }
}