package com.edzeban.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by gidis on 2/6/15.
 */
public class FirstLaunchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
  /**      new AlertDialog.Builder(this)
                .title(R.string.title)
                .items(R.array.items)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMulti() {
                    @Override
                    public void onSelection(MaterialDialog dialog, Integer[3] which, CharSequence[] text) {
                    }
                })
                .positiveText(R.string.choose)
                .show();*/
    }
}
