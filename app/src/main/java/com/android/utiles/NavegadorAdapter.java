package com.android.utiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.coparmextab.R;

/**
 * Adaptador utilizado para mostrar la lista de las distintas secciones
 * que existen en el NavigationDrawer
 *
 * Created by OscarEnrique on 02/12/2015.
 */
public class NavegadorAdapter extends BaseAdapter {

    private Context contexto;
    private ItemNavegador[] items;

    public NavegadorAdapter(Context contexto, ItemNavegador[] items) {
        this.contexto = contexto;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }


    /**
     * Devuelve un identificador único del elemento mediante una función de
     * hashcode.
     *
     * @param position la posición de item del que se desea obtener el id.
     *
     * @return El identificador único del elemento en la posición pasada como argumento.
     */

    @Override
    public long getItemId(int position) {
        return (items[position].getTitulo() + items[position].getIcono().getLevel() +
                items[position].getIcono().toString()).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View fila = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) contexto
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            fila = inflater.inflate(R.layout.drawer_list_item, parent, false);

        } else {
            fila = convertView;
        }


        TextView tvTitulo =  (TextView) fila.findViewById(R.id.drawer_titulo);
        ImageView ivIcono = (ImageView) fila.findViewById(R.id.drawer_icono);

        tvTitulo.setText(items[position].getTitulo());
        ivIcono.setImageDrawable(items[position].getIcono());
        return fila;
    }
}
