package com.android.coparmextab.UserInterface;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.coparmextab.R;

public class MainScreenBotFragment extends Fragment {

    private static View rootview;

    public MainScreenBotFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootview == null) {
            rootview = inflater.inflate(R.layout.main_screen_base_layout, container, false);
        }
        return rootview;
    }

}
