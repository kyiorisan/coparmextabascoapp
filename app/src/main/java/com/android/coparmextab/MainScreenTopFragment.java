package com.android.coparmextab;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.coparmextab.ListaEmpresas.EmpresaFragment;
import com.android.coparmextab.ListaOfertas.OfertaFragment;
import com.android.coparmextab.R;
import com.android.utiles.OnFragmentInteractionListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainScreenTopFragment extends Fragment {


    private static View rootview;
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.radio0) RadioButton RadioOfertas;
    @BindView(R.id.radio1) RadioButton RadioEmpresas;
    @BindView(R.id.radio2) RadioButton RadioProduct;
    @BindView(R.id.textoBusqueda) TextView TextoBuscado;




    public MainScreenTopFragment() {
        // Required empty public constructor
    }

    public static MainScreenTopFragment getInstance(OnFragmentInteractionListener listener){
        MainScreenTopFragment instance = new MainScreenTopFragment();
        instance.setmListener(listener);
        return instance;
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
            ButterKnife.bind(this,rootview);
        }
        return rootview;
    }

    @OnClick(R.id.radio0) public void CambioFragmentoOferta(View v){
        if(RadioOfertas.isChecked()){
            mListener.onFragmentInteraction(new OfertaFragment(),"Ofertas");
        }
    }

    @OnClick(R.id.radio1) public void CambioFragmentoEmpresa(View v){
        if(RadioEmpresas.isChecked())
            mListener.onFragmentInteraction(new EmpresaFragment(),"Empresas");
    }

    @OnClick(R.id.radio2) public void CambioFragmentoProductos(View v){
        if(RadioProduct.isChecked())
            Toast.makeText(getActivity(), "Testeo", Toast.LENGTH_SHORT).show();;
    }


    /**
     * Cambia el fragment de la parte inferior {@link MainScreenBotFragment} basandose en el
     * la búsqueda introducida en el campo de texto: {@link com.android.coparmextab.R.id#textoBusqueda}
     */

    @OnClick(R.id.botonBuscar) public void cambioVista() {
        if (RadioOfertas.isChecked()) {
            // llamado a query de oferta
            Toast.makeText(getActivity(), "Ofertas", Toast.LENGTH_SHORT).show();// TODO: Borrar este toast de prueba cuando se implemente el comportamiento de este caso de uso.
            mListener.onFragmentInteraction(OfertaFragment.newInstance(1),"Ofertas");
        }

        if (RadioEmpresas.isChecked()) {
            Toast.makeText(getActivity(), "Empresas", Toast.LENGTH_SHORT).show();
            mListener.onFragmentInteraction(EmpresaFragment.newInstance(1, TextoBuscado.getText().toString(),(MainActivity)getActivity()),"Empresas");
        }

        if (RadioProduct.isChecked()) {
            // llamado a query de productos o servicios
            Toast.makeText(getActivity(), "Productos", Toast.LENGTH_SHORT).show();// TODO-ME Borrar este toast de prueba cuano se implemente el comportamiento de este caso de uso.
            // getFragmentManager().beginTransaction().replace(R.id.bottom_fragment, ListaEmpresasFragment.newInstance()).commit();
        }
    }


    public void setmListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }
}
