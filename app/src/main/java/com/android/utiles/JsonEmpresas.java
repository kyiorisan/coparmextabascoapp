package com.android.utiles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Clase que almacena los datos devueltos por un json de respuesta enviado al servidor al realizar una consulta al servidor.
 *
 * Created by OscarEnrique on 13/01/2016.
 */
public class JsonEmpresas {

    //<editor-fold desc="Atributos">
    /**
     * Número de empresas en total que devuelve la consulta.
     */
    @SerializedName("total")
    @Expose
    public int total;

    /**
     * Lista de empresas que devuelve la consulta.
     */
    @SerializedName("rows")
    @Expose
    List<Empresa> empresas;
    //</editor-fold>

    //<editor-fold desc="Getters">
    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public int getTotal() {
        return total;
    }
    //</editor-fold>

    //<editor-fold desc="Setters">
    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    //</editor-fold>
}
