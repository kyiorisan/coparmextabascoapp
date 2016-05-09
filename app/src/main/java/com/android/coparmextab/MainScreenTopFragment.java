package com.android.coparmextab;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.android.coparmextab.R;


public class MainScreenTopFragment extends Fragment {


    private static View rootview;
    private static RadioButton radio1;
    private static RadioButton radio2;
    private static RadioButton radio3;

    public MainScreenTopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(rootview == null) {
            rootview =  inflater.inflate(R.layout.fragment_main_top, container, false);
            radio1 = (RadioButton)rootview.findViewById(R.id.radio0);
            radio2 = (RadioButton)rootview.findViewById(R.id.radio1);
            radio3 = (RadioButton)rootview.findViewById(R.id.radio2);
        }
        return rootview;
    }

}
