package com.android.utiles;

import android.graphics.drawable.Drawable;

/**
 * Clase que representa cada elemento del NavigationDrawer en la actividad principal.
 *
 * Created by OscarEnrique on 02/12/2015.
 */
public class ItemNavegador {

    //<editor-fold desc="Atributos">
    /**
     * Icono que muestra del lado izquiero el Navigation Drawer en cada objeto.
     */
    private Drawable icono;

    /**
     * El nombre del objeto, el cual representa uno de los módulos de la aplicación.
     */

    private String titulo;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public ItemNavegador(String titulo, Drawable icono) {
        this.titulo = titulo;
        this.icono = icono;
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public Drawable getIcono() {
        return icono;
    }

    public String getTitulo() {
        return titulo;
    }
    //</editor-fold>
}
