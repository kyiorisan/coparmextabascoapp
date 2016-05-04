package com.android.coparmextab.UserInterface;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.coparmextab.ListaEmpresas.EmpresaFragment;
import com.android.coparmextab.ListaEmpresas.MyEmpresaRecyclerViewAdapter;
import com.android.coparmextab.R;
import com.android.retrofitutils.ClienteRest;
import com.android.retrofitutils.ClienteRestTest;
import com.android.retrofitutils.EmpresaService;
import com.android.utiles.DBManager;
import com.android.utiles.Empresa;
import com.android.utiles.JsonEmpresas;
import com.android.utiles.OnFragmentInteractionListener;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by OscarEnrique on 03/03/2016.
 */
public class FavoritosFragment extends Fragment implements MyEmpresaRecyclerViewAdapter.FragmentInteractionListener {

    public List<Empresa> retornoList;

    public List<Empresa> list;

    public DBManager database;

    public RecyclerView recyclerView;

    private MyEmpresaRecyclerViewAdapter mrva;

    private ActionMode actionMode;

    private ActionModeCallBack actionModeCallBack = new ActionModeCallBack();

    OnFragmentInteractionListener mlisterner;

    public FavoritosFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new DBManager(getActivity());
        setHasOptionsMenu(true);
    }

    public void setRetornoList(List<Empresa> retornoList) {
        this.retornoList = retornoList;
    }

    public void setListerner(OnFragmentInteractionListener mlisterner) {
        this.mlisterner = mlisterner;
    }

    public static FavoritosFragment newInstance(List<Empresa> retorno,OnFragmentInteractionListener listener) {
        Bundle args = new Bundle();
        FavoritosFragment fragment = new FavoritosFragment();
        fragment.setArguments(args);
        fragment.setRetornoList(retorno);
        fragment.setListerner(listener);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empresa_list, container, false);
        // Sets the adapter and layoutmanager

            Context context = view.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.empresarecyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            actualizarFavoritos(database);
            list = DBManager.getEmpresasFavorites(database);
            mrva = new MyEmpresaRecyclerViewAdapter(list,this);
            recyclerView.setAdapter(mrva);
            makelistVisible(view);

        getActivity().getActionBar().setTitle("Favoritos");
        return view;
    }


    public void makelistVisible(View view){
        view.findViewById(R.id.empresarecyclerview).setVisibility(View.VISIBLE);
        view.findViewById(R.id.Recycler_progress_bar).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO actualizar la creación de la db en el oncreate de la main activity.
        //mrva.actualizarListaDatos(DBManager.getEmpresasFavorites(database));
    }

    private void actualizarFavoritos(DBManager database) {
        List<Empresa> favs = DBManager.getEmpresasFavorites(database);

        final DBManager dbManager = database;
        for(final Empresa e : favs){
            Call<JsonEmpresas> queryToServer = ClienteRest.getEmpresaservice().buscarPorId(e.getRfc());
            queryToServer.enqueue(new Callback<JsonEmpresas>() {
                @Override
                public void onResponse(Response<JsonEmpresas> response) {
                    DBManager.updateEmpresa(dbManager,response.body().getEmpresas().get(0));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("Retrofit 2.0:","no se pudo acceder a los datos "+t.getMessage());
                    return;
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.lista_favoritos,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.regreso){
            mlisterner.onFragmentInteraction(EmpresaFragment.newInstance(1,(ArrayList)retornoList,mlisterner),"Empresas");
            return true;
        }
        else {
           return  super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(View v, int position) {
        final View cv = v;
        final Intent intent = new Intent(v.getContext(), InfoEmpresaActivity.class);
        intent.putExtra("EMPRESA", (Empresa) v.getTag());
        cv.getContext().startActivity(intent);
    }

    @Override
    public void onItemLongPress(View v, int position) {
        if(actionMode != null){
            return;
        }
        actionMode = getActivity().startActionMode(actionModeCallBack);
        empresaToggleSelection(position);
    }

    private class ActionModeCallBack implements ActionMode.Callback {

        @SuppressWarnings("unused")
        private final String etiqueta = ActionModeCallBack.class.getSimpleName();


        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_favoritos, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {

                case R.id.delete:
                    if (mrva.getSelectedPos() != -1) {
                        borrarEmpresaFavorita(mrva.getId(mrva.getSelectedPos()));
                        mode.finish();
                        mrva.deselect();
                    }
                    return true;

                default:
                    return true;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            mrva.deselect();
        }

    }

    private void borrarEmpresaFavorita(String id) {
        DBManager db = new DBManager(getContext());
        if(DBManager.deleteEmpresaFavorites(db,id)){
            mrva.actualizarListaDatos(DBManager.getEmpresasFavorites(db));
            Toast.makeText(getContext(),"favorito ha sido borrado",Toast.LENGTH_SHORT);
        }

    }

    private void empresaToggleSelection(int pos) {
        mrva.toggleSelection(pos);
        String title = String.valueOf(mrva.getSelectedPos() + 1);
        actionMode.setTitle("Favorito " + title + " Selec.");
    }
}
