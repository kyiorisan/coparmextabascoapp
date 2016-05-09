package com.android.coparmextab;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.utiles.OnFragmentInteractionListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainScreenBotFragment extends Fragment {

    private static View rootview;

    private OnFragmentInteractionListener mListener;

    public MainScreenBotFragment() {

    }


    public static MainScreenBotFragment getInstance(OnFragmentInteractionListener listener) {
        MainScreenBotFragment instance = new MainScreenBotFragment();
        instance.setmListener(listener);
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootview == null) {
            rootview = inflater.inflate(R.layout.main_screen_base_layout, container, false);
            ButterKnife.bind(rootview);
        }
        return rootview;
    }

    /**
     * Cambiar por el fragment de registro en la pantalla principal mediante el botón de registro
     * de la parte inferior de la pantalla.
     */

    /*
    @OnClick(R.id.buttonRegistro)
    public void runRegistro() {
        mListener.onFragmentInteraction(new RegistroFragment(),"");
    }*/

    public void setmListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }
}

