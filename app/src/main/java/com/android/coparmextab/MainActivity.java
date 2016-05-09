package com.android.coparmextab;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.coparmextab.ListaEmpresas.EmpresaFragment;
import com.android.coparmextab.ListaOfertas.OfertaFragment;
import com.android.utiles.DBManager;
import com.android.utiles.Empresa;
import com.android.utiles.Oferta;
import com.android.utiles.OnFragmentInteractionListener;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks,OnFragmentInteractionListener {

    //<editor-fold desc="Atributos">
    /**
     * Fragment que administra los comportamientos, interacciones y presentación del
     * navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Usado para almacenar el último título de pantalla en
     * {@link #restoreActionBar()}.
     */
    private static View rootview;
    private CharSequence mTitle;
    private String[] actividades;
    private long backPressedTimer=0;
    private Fragment botFragment = getFragmentManager().findFragmentById(R.id.bottom_fragment);
    //</editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!DBManager.checkDbExists()) {
            new DBManager(this);
        }
        actividades = getResources().getStringArray(R.array.actividades);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.bottom_fragment, new MainScreenBotFragment());
        ft.add(R.id.top_fragment, MainScreenTopFragment.getInstance(this));
        ft.commit();
        ButterKnife.bind(this);
    }





    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        switch (position) {

            case 0:
                fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position))
                        .commit();
                if(botFragment instanceof OfertaFragment || botFragment instanceof EmpresaFragment){
                    fragmentManager.beginTransaction().replace(R.id.bottom_fragment, new MainScreenBotFragment()).commit();
                    mTitle = "Coparmex Tabasco";
                }
                break;

            case 1:
                fragmentManager.beginTransaction().replace(R.id.container, new Empresas10Fragment()).commit();
                mTitle = actividades[position];
                break;

            case 2:
                fragmentManager.beginTransaction().replace(R.id.container, new TerminosCondicionesFragment()).commit();
                mTitle = actividades[position];
                break;

            case 3:
                fragmentManager.beginTransaction().replace(R.id.container, new SociosCoparmexFragment()).commit();
                mTitle = actividades[position];
                break;

            case 4:
                fragmentManager.beginTransaction().replace(R.id.container, new RegistroFragment()).commit();
                mTitle = actividades[position];
                break;

            case 5:
                fragmentManager.beginTransaction().replace(R.id.container,new AcercaDeFragment()).commit();
                mTitle = actividades[position];
                invalidateOptionsMenu();
                break;

            case 6:
                fragmentManager.beginTransaction().replace(R.id.container, new ContactoFragment()).commit();
                mTitle = actividades[position];
                invalidateOptionsMenu();
                break;

            default:
                mTitle = actividades[position];
                invalidateOptionsMenu();
                break;
        }
    }


    /**
     * Establece el título de la sección.
     */
    public void onSectionAttached(int number) {
        mTitle = actividades[number];
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        mTitle = actividades[4];
    }

    @Override
    public void onFragmentInteraction(Fragment fragment, String title) {
            getFragmentManager().beginTransaction().replace(R.id.bottom_fragment, fragment, "BotFragment").commit();
            mTitle = title;
    }

    @Override
    public void onFragmentInteraction(Oferta oferta) {

    }

    @Override
    public void onFragmentInteraction(Empresa empresa) {

    }

    @Override
    public void onBackPressed() {
        if(backPressedTimer + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Toast.makeText(getBaseContext(),"Presione atrás nuevamente para salir.",Toast.LENGTH_SHORT)
                    .show();
        }
        backPressedTimer = System.currentTimeMillis();

    }

    /**
     * Un fragmento para tomar lugar con una vista simple
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            if (rootview == null) {
                rootview = inflater.inflate(R.layout.fragment_main, container, false);
            }

            return rootview;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO componer el restablecimiento de la app para que muestre el banner de inicio.
        //getFragmentManager().beginTransaction().replace(R.id.bottom_fragment, new MainScreenBotFragment())
        //        .commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }






}
