package com.android.utiles;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Esta clase representa un punto en el mapa.
 *
 * Created by OscarEnrique on 13/01/2016.
 */


public class Georef implements Parcelable {

    /**
     * {@code double} que representa la latitud de una ubicacion.
     */
    @SerializedName("lat")
    @Expose
    double lat;


    /**
     * {@code double} que representa la longitud de una ubicacion.
     */
    @SerializedName("lon")
    @Expose
    double lon;

    //region Constructores
    public Georef(){
    }


    public Georef(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
    //endregion


    /**
     * Devuelve la latitud del geopunto.
     *
     * @return un {@code double} con la latitud del geopunto.
      */
    public double getLat() {
        return lat;
    }

    /**
     * Devuelve la longitud del geopunto.
     * @return un {@code double} con la longitud del geopunto.
     */
    public double getLon() {
        return lon;
    }

    /**
     * Establece la latitud del geopunto.
     * @param lat la latitud.
     *
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Establece la longitud del geopunto.
     * @param lon la longitud.
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * Transoforma un {@link Georef} en un objeto de la clase {@link LatLng} para su uso con el
     * api de google maps.
     *
     * @param georef el {@link Georef} a convertir.
     *
     * @return un objeto de la clase {@link LatLng} equivalente.
     */

    public static LatLng toLatLng(Georef georef){
        return new LatLng(georef.getLat(),georef.getLon());
    }

    //region Implementacion de Parcelable
    protected Georef(Parcel in) {
        lat = in.readDouble();
        lon = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lon);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Georef> CREATOR = new Parcelable.Creator<Georef>() {
        @Override
        public Georef createFromParcel(Parcel in) {
            return new Georef(in);
        }

        @Override
        public Georef[] newArray(int size) {
            return new Georef[size];
        }
    };
    //endregion
}
