package com.edzeban.app.fragment;

import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edzeban.app.R;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by gidis on 2/6/15.
 */
public class AboutFragment extends Fragment {
    public AboutFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about,
                container, false);
        final MaterialDialog mMaterialDialog = new MaterialDialog(getActivity())
                .setTitle("About")

                .setMessage(R.string.dialog_string);





        mMaterialDialog.show();

        return rootView;

    }

}
