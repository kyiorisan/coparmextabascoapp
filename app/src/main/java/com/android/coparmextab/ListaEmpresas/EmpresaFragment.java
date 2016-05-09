package com.android.coparmextab.ListaEmpresas;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.coparmextab.FavoritosFragment;
import com.android.coparmextab.InfoEmpresaActivity;
import com.android.coparmextab.R;
import com.android.retrofitutils.ClienteRest;
import com.android.retrofitutils.ClienteRestTest;
import com.android.utiles.Empresa;
import com.android.utiles.JsonEmpresas;
import com.android.utiles.JsonRateResponse;
import com.android.utiles.OnFragmentInteractionListener;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment que contiene la lista de empresas obtenida según la búsqueda introducida.
 */
public class EmpresaFragment extends Fragment implements MyEmpresaRecyclerViewAdapter.FragmentInteractionListener {

    //<editor-fold desc="Atributos">
    private static final String SAVED_STATE = "SavedEstate";
    private static final String ARG_QUERY = "JSON-Query";
    private static final String ARG_LIST = "List";
    private static Bundle mBundleRVState;
    private int mColumnCount = 1;
    private List<Empresa> lista;
    private RecyclerView recyclerView;
    private MyEmpresaRecyclerViewAdapter mrva;
    private ActionMode actionMode;
    private ActionModeCallBack actionModeCallBack = new ActionModeCallBack();
    private OnFragmentInteractionListener mlistener;


    //</editor-fold>


    //<editor-fold desc="Constructors & Factory">

    /**
     * Constructor vacío obligatorio usado por el administrador de fragments.
     */
    public EmpresaFragment() {

    }


    /**
     * Método para fabricar un fragmento de empresas
     *
     * @param query El texto de la consulta hecha en la actividad principal
     * @param listener Listener usado para cabiar el fragmento y el título del ActionBar.
     * @return el fragmento instanciado.
     */
    public static EmpresaFragment newInstance(int columnCount, String query, OnFragmentInteractionListener listener) {
        EmpresaFragment fragment = new EmpresaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUERY, query);
        fragment.setArguments(args);
        fragment.setMlistener(listener);
        fragment.setHasOptionsMenu(true);
        return fragment;
    }


    /**
     *
     * @param lista
     * @param listener
     * @return
     */
    public static EmpresaFragment newInstance(ArrayList<Empresa> lista, OnFragmentInteractionListener listener) {
        EmpresaFragment fragment = new EmpresaFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_LIST, lista);
        fragment.setArguments(args);
        fragment.setMlistener(listener);
        fragment.setHasOptionsMenu(true);
        return fragment;
    }
    //</editor-fold>

    //<editor-fold desc="Lifecycle">

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empresa_list, container, false);


        // Set the adapter

            Context context = view.getContext();
            recyclerView =  (RecyclerView) view.findViewById(R.id.empresarecyclerview);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //cuando es vista de favoritos.
            if (getArguments().getParcelableArrayList(ARG_LIST) != null) {
                lista = getArguments().getParcelableArrayList(ARG_LIST);
                mrva = new MyEmpresaRecyclerViewAdapter(lista,this);
                recyclerView.setAdapter(mrva);

            }
            //cuando la vista es llamada desde el servidor.
            else {
                retrofitjsoncall();
                //retrofitTestjsoncall();
                mrva = new MyEmpresaRecyclerViewAdapter(new ArrayList<Empresa>(),this);
                recyclerView.setAdapter(mrva);
            }
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.favlist) {
            mlistener.onFragmentInteraction(FavoritosFragment.newInstance(lista, mlistener), "Favoritos");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mBundleRVState = new Bundle();
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRVState.putParcelable(SAVED_STATE,listState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mBundleRVState !=null){
            Parcelable listState = mBundleRVState.getParcelable(SAVED_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.lista_empresas, menu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //</editor-fold>

    public void setMlistener(OnFragmentInteractionListener mlistener) {
        this.mlistener = mlistener;
    }

    public void setVisibleInterfaceList(){
        getView().findViewById(R.id.Recycler_progress_bar).setVisibility(View.INVISIBLE);
        getView().findViewById(R.id.empresarecyclerview).setVisibility(View.VISIBLE);
    }

    //<editor-fold desc="Data">

    /**
     * Llamado al servidor.
     */
    public void retrofitjsoncall() {
        //Calls the web service to get the "empresas" stored in the server that matches the criteria.
        Call<JsonEmpresas> call1 = ClienteRest.getEmpresaservice().buscarPorNombre(getArguments().getString(ARG_QUERY));
        call1.enqueue(new Callback<JsonEmpresas>() {
            @Override
            public void onResponse(Response<JsonEmpresas> response) {
                //returns the list and updates the data in the adapter.
                Log.d("Json Result- ", String.valueOf(response.code()));
                if(response.body().getEmpresas()!=null) {
                    mrva.actualizarListaDatos(response.body().getEmpresas());
                    setVisibleInterfaceList();
                }

                else{
                    Toast.makeText(getActivity(), "Error en el servidor...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

                if (t.getClass() == UnknownHostException.class) {
                    //if the webhost can't be reached, throw this message.
                    Toast.makeText(getActivity(), "El servidor no está diponible en estos momentos...", Toast.LENGTH_SHORT).show();
                    setVisibleInterfaceList();
                } else {
                    //if there's no internet conection, throw this message
                    Toast.makeText(getActivity(), "No se pudo acceder al servidor...", Toast.LENGTH_SHORT).show();
                    setVisibleInterfaceList();
                }
            }

        });
    }

    /**
     * Uso local en conjunto con el servidor de pruebas
     */
    public void retrofitTestjsoncall() {
        //Calls the web service to get the "empresas" stored in the server that matches the criteria.
        Call<JsonEmpresas> call1 = ClienteRestTest.getEmpresaservice().getEmpresas();
        call1.enqueue(new Callback<JsonEmpresas>() {
            @Override
            public void onResponse(Response<JsonEmpresas> response) {
                //returns the list and updates the data in the adapter.
                Log.d("Json Result- ", String.valueOf(response.code()));
                if(response.body().getEmpresas()!=null) {
                    mrva.actualizarListaDatos(response.body().getEmpresas());
                    setVisibleInterfaceList();
                }

            }

            @Override
            public void onFailure(Throwable t) {

                if (t.getClass() == UnknownHostException.class) {
                    //if the webhost can't be reached, throw this message.
                    Toast.makeText(getActivity(), "El servidor no está diponible en estos momentos...", Toast.LENGTH_SHORT).show();
                    setVisibleInterfaceList();
                } else {
                    //if there's no internet conection, throw this message
                    Toast.makeText(getActivity(), "No se pudo acceder al servidor...", Toast.LENGTH_SHORT).show();
                    setVisibleInterfaceList();
                }
            }

        });
    }


    //</editor-fold>


    //<editor-fold desc="Barra Superior">
    private void empresaToggleSelection(int pos) {
        mrva.toggleSelection(pos);
        String title = String.valueOf(mrva.getSelectedPos() + 1);
        actionMode.setTitle("Empresa " + title + " Selec.");
    }

    @Override
    public void onItemClick(View v, int position) {
        final View cv = v;
        final Intent intent = new Intent(v.getContext(), InfoEmpresaActivity.class);
        Call<JsonEmpresas> query = ClienteRest.getEmpresaservice().buscarPorId(((Empresa) v.getTag()).getRfc());
        query.enqueue(new Callback<JsonEmpresas>() {
            @Override
            public void onResponse(Response<JsonEmpresas> response) {
                intent.putExtra("EMPRESA", response.body().getEmpresas().get(0));
                cv.getContext().startActivity(intent);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Retrofit: ",t.getMessage(),t);
            }
        });
    }

    @Override
    public void onItemLongPress(View v, int position) {
        if (actionMode != null) {
            actionMode.setTitle("Empresa " + (position+1) + " Selec.");
            return;
        }
        if(position == mrva.getSelectedPos()){
            v.setSelected(false);
            mrva.setSelectedPos(-1);
        }
        else{
            mrva.setSelectedPos(position);
        }
        actionMode = getActivity().startActionMode(actionModeCallBack);
        empresaToggleSelection(position);
    }


    private class ActionModeCallBack implements ActionMode.Callback {

        @SuppressWarnings("unused")
        private final String etiqueta = ActionModeCallBack.class.getSimpleName();


        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_empresa, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {

                case R.id.rate:
                    if (mrva.getSelectedPos() != -1) {
                        onCreateDialog(mrva.getId(mrva.getSelectedPos()));
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

    public void onCreateDialog(final String rfc) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.rate_empresa_dialog);
        dialog.show();
        final int position = mrva.getSelectedPos();
        final RadioButton radioPositivo = (RadioButton) dialog.findViewById(R.id.radio_positivo);
        final RadioButton radioNegativo = (RadioButton) dialog.findViewById(R.id.radio_negativo);
        Button botonEnviar = (Button) dialog.findViewById(R.id.boton_enviar);
        Button botonCancelar = (Button) dialog.findViewById(R.id.boton_cancelar);

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valoracion = radioPositivo.isChecked() ? "0" : "1";
                final String id = rfc;
                //no se usan por por ahora los comentarios a empresas.
                //todo-me implementar comentarios a empresas.
                if (!(radioPositivo.isChecked() | radioNegativo.isChecked())) {
                    Toast.makeText(getActivity(), "Marque una de las valoraciones por favor...", Toast.LENGTH_LONG).show();
                } else {
                    Call<JsonRateResponse> response = ClienteRest.getEmpresaservice().valorarEmpresaAlt(valoracion, id);
                    response.enqueue(new Callback<JsonRateResponse>() {
                        @Override
                        public void onResponse(Response<JsonRateResponse> response) {
                            Toast.makeText(getActivity(), response.message() + " a " + id, Toast.LENGTH_LONG).show();
                            mrva.updateItem(position, rfc);
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    dialog.dismiss();
                }
            }
        });

        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mrva.deselect();
                dialog.cancel();
            }
        });
    }
    //</editor-fold>


}
