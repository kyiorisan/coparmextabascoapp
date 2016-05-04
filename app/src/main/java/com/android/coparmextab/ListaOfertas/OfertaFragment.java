package com.android.coparmextab.ListaOfertas;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.coparmextab.R;
import com.android.retrofitutils.ClienteRest;
import com.android.utiles.Empresa;
import com.android.utiles.JsonEmpresas;
import com.android.utiles.JsonOfertas;
import com.android.utiles.Oferta;
import com.android.utiles.Vigencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class OfertaFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";

    private static final String ARG_QUERY = "JSON-Query";

    private List<Oferta> lista;

    // TODO: Customize parameters
    private int mColumnCount = 1;



    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OfertaFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OfertaFragment newInstance(int columnCount) {
        OfertaFragment fragment = new OfertaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeItems();
        lista = retrofitJsonOferta();
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oferta_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyofertaRecyclerViewAdapter(lista, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Oferta oferta);
    }

    public void initializeItems(){
        /*
        lista = new ArrayList<>();
        lista.add(new Oferta("10% de descuento en avaluos","15% socios coparmex",new Vigencia(new Date(2015,5,20)
                ,new Date(2015,6,10)),"http://www.oferta1.com"));
        lista.add(new Oferta("5% de descuento en llantas","10% de descuento socios coparmex",new Vigencia(new Date(2015,6,20)
                ,new Date(2015,6,30)),"http://www.oferta2.com"));
        lista.add(new Oferta("2x1 en desayunos","10% de descuento adicional para socios coparmex",new Vigencia(new Date(2015,5,20)
                ,new Date(2015,6,10)),"http://www.oferta3.com"));
        */

    }

    public List<Oferta> retrofitJsonOferta(){

        final List<Oferta> tlist = new ArrayList();
        Call<JsonOfertas> call = ClienteRest.getOfertaservice().getOfertas();
        call.enqueue(new Callback<JsonOfertas>() {
            @Override
            public void onResponse(Response<JsonOfertas> response) {
                tlist.addAll(response.body().getOfertas());
            }

            @Override
            public void onFailure(Throwable t) {
                //Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }


        });

        return tlist;



    }



}
