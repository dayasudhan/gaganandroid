package com.example.gagan.khanavali_main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gagan on 11/6/2015.
 */
public class menu2_layout_profile extends Fragment {

    View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       rootview=inflater.inflate(R.layout.profile,container,false);
        return rootview;
    }
}
