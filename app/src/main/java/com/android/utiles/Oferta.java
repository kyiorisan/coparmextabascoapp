package com.android.utiles;


import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Created by OscarEnrique on 08/12/2015.
 */
public class Oferta {

    //<editor-fold desc="Atributos">

    /**
     * RFC utilizado para enlazar con la empresa deseada.
     */
    @SerializedName("RFC")
    String RFC_Empresa;

    /**
     * El identificador único de la oferta.
     */

    @SerializedName("ID")
    String id;

    /**
     * Texto de la oferta utilizado para búsqueda de ofertas.
     */
    @SerializedName("TEXT")
    String texto;


    /**
     * Imágen de la oferta mostrada en la lista de ofertas.
     */
    @SerializedName("BANNER")
    String banner;

    /**
     * Fecha de inicio de la oferta.
     */
    @SerializedName("FINI")
    Date Fini;

    /**
     * Fecha en que la oferta finaliza.
     */
    @SerializedName("FEND")
    Date Fend;

    /**
     * Página web con la descripción de la oferta.
     */
    @SerializedName("WEB")
    String Web;

    /**
     * Valor automático generado en el constructor de la oferta. Indica la vigencia de la
     * oferta.
     */
    boolean vigente;
    //</editor-fold>

    //<editor-fold desc="Constructores">
    public Oferta( String RFC_Empresa, String id, String texto, String banner, Date fini, Date fend, String web) {
        this.RFC_Empresa = RFC_Empresa;
        this.id = id;
        this.texto = texto;
        this.banner = banner;
        Fini = fini;
        Fend = fend;
        Web = web;
        //this.vigente = vigente;
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public String getRFC_Empresa() {
        return RFC_Empresa;
    }

    public String getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public String getBanner() {
        return banner;
    }

    public Date getFini() {
        return Fini;
    }

    public Date getFend() {
        return Fend;
    }

    public String getWeb() {
        return Web;
    }

    public boolean isVigente(){
        return vigente;
    }
    //</editor-fold>

    //<editor-fold desc="Setters">
    public void setRFC_Empresa(String RFC_Empresa) {
        this.RFC_Empresa = RFC_Empresa;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setFini(Date fini) {
        Fini = fini;
    }

    public void setFend(Date fend) {
        Fend = fend;
    }

    public void setWeb(String web) {
        Web = web;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }
    //</editor-fold>
}
