package com.android.utiles;

import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Esta clase contiene los valores devueltos por el json de ofertas del servidor.
 *
 * Created by OscarEnrique on 16/01/2016.
 */
public class JsonOfertas {

    //<editor-fold desc="Atributos">
    @SerializedName("total")
    int total;

    @SerializedName("rows")
    List<Oferta> ofertas;

    //</editor-fold>

    //<editor-fold desc="Constructores">
    public JsonOfertas(int total, List<Oferta> ofertas) {
        this.total = total;
        this.ofertas = ofertas;
    }
    //</editor-fold>

    //<editor-fold desc="Setters">
    public void setTotal(int total) {
        this.total = total;
    }

    public void setOfertas(List<Oferta> ofertas) {
        this.ofertas = ofertas;
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public int getTotal() {
        return total;
    }

    public List<Oferta> getOfertas() {
        return ofertas;
    }
    //</editor-fold>


}
