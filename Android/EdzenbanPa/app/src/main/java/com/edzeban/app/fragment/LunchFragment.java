package com.edzeban.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edzeban.app.R;

/**
 * Created by gidis on 2/6/15.
 */
public class LunchFragment extends Fragment {
    public LunchFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_lunch,
                container, false);
        return rootView;
    }
}
