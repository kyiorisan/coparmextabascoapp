package com.android.coparmextab.UserInterface;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.coparmextab.R;

public class Empresas10Fragment extends Fragment {

	private View vista;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		vista = inflater.inflate(R.layout.empresas_10, container, false);
		return vista;
	}


}
