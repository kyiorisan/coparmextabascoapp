package com.android.coparmextab;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.coparmextab.R;

import butterknife.OnClick;

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

    @OnClick(R.id.button1)
    public void test(){
        Toast.makeText(this.getContext(),"izi putos",Toast.LENGTH_SHORT).show();
    }
}
