package com.example.gagan.khanavali_main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by gagan on 11/6/2015.
 */
public class menu4_layout_logout extends Fragment {

    View rootview;

    // Session Manager Class
    SessionManager session;
    // Button Logout
    Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.logout,container,false);
        // Session class instance
        session = new SessionManager(getActivity().getApplicationContext());


        // Button logout
        btnLogout = (Button)rootview.findViewById(R.id.btnLogout);
        // get user data from session
        /**
         * Logout button click event
         * */
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                session.logoutUser();
            }
        });

        return rootview;
    }
}
