package com.android.utiles;

import android.app.Fragment;
import android.net.Uri;

/**
 * Created by OscarEnrique on 28/01/2016.
 */
public interface OnFragmentInteractionListener {
   void onFragmentInteraction(Uri uri);

   void onFragmentInteraction(Fragment fragment, String title);

   void onFragmentInteraction(Oferta oferta);

   void onFragmentInteraction(Empresa empresa);
}
