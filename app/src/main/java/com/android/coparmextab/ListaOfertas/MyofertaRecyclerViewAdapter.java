package com.android.coparmextab.ListaOfertas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.coparmextab.R;
import com.android.utiles.MyApp;
import com.android.utiles.Oferta;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * {@link RecyclerView.Adapter} que puede mostrar una {@link Oferta} y hacer un llamado al
 *  {@link } específico.
 *
 */
public class MyofertaRecyclerViewAdapter extends RecyclerView.Adapter<MyofertaRecyclerViewAdapter.ViewHolder> {

    private final List<Oferta> mValues;
    private final OfertaFragment.OnListFragmentInteractionListener mListener;
    private Context ctx;

    public MyofertaRecyclerViewAdapter(List<Oferta> items, OfertaFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.oferta_item, parent, false);
        ctx= view.getContext();
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);//asigna el item al elemento
        holder.mOfertaTexto.setText(holder.mItem.getTexto());// asigna el texto de oferta
        Picasso.with(ctx)
                .load(holder.mItem.getBanner())
                .into(holder.mOfertaBanner);//librería usada para establecer la imágen.
        //holder.mOfertaBanner.setImageDrawable();
        holder.mOfertaVigencia.setText(holder.mItem.getFini()+" a "+holder.mItem.getFend());//establecer la vigencia.
        holder.mOfertaVermas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.mItem.getWeb()));
                v.getContext().startActivity(i);
            }
        });


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
           //         mListener.onListFragmentInteraction(holder.mItem);
                    Toast.makeText(v.getContext(),"testing",Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.mView.setBackgroundColor(MyApp.getStaticResources().getColor(R.color.btnFBRegistro));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        /**
         * Texto de la oferta.
         */
        public final TextView mOfertaTexto;


        /**
         * Imagen de la oferta.
         */
        public final ImageView mOfertaBanner;

        /**
         * Texto de vigencia de la oferta.
         */
        public final TextView mOfertaVigencia;

        /**
         * El enlace a la página web.
         */

        public final TextView mOfertaVermas;


        /**
         * Objeto de oferta que contiene los datos.
         */

        public Oferta mItem;


        /**
         * Inicializador de la oferta.
         *
         * @param view
         */
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mOfertaTexto = (TextView) view.findViewById(R.id.ofertaTexto);
            mOfertaBanner = (ImageView) view.findViewById(R.id.oferta_imagen_oferta);
            mOfertaVigencia = (TextView) view.findViewById(R.id.oferta_vigencia);
            mOfertaVermas = (TextView) view.findViewById(R.id.vermas);
        }

    }
}
