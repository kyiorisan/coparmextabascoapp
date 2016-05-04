package com.android.utiles;

import java.util.Date;

/**
 *
 * @deprecated
 * por el formato de los Json de oferta, fue sustituida por atributos directos en la clase
 * oferta.
 *
 * Created by OscarEnrique on 25/12/2015.
 */
public class Vigencia {
    private Date inicio;
    private Date expiracion;
    private boolean isVigente;

    public Vigencia(Date inicio, Date expiracion) {
        this.inicio = inicio;
        this.expiracion = expiracion;
        //TODO Sustituir la implementación por un método que auto establezca la vigencia de la oferta.
        this.isVigente = true;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getExpiracion() {
        return expiracion;
    }

    public void setExpiracion(Date expiracion) {
        this.expiracion = expiracion;
    }

    @Override
    public String toString() {
        return inicio.getDate()+"/"+ inicio.getMonth()+"/"+inicio.getYear()+" al "
                +expiracion.getDate()+"/"+expiracion.getMonth()+"/"+expiracion.getYear();
    }

    public boolean isVigente() {
        return isVigente;
    }
}
