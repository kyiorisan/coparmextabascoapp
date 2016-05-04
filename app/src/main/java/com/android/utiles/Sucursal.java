package com.android.utiles;


import android.os.Parcel;
import com.google.android.gms.maps.model.LatLng;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Clase que representa una sucursal perteneciente a una empresa.
 *
 * Created by OscarEnrique on 24/12/2015.
 */
public class Sucursal implements Parcelable {


    @SerializedName("id")
    String name;


    @SerializedName("addr")
    String address;


    @SerializedName("phone")
    String phone;


    @SerializedName("georef")
    @Expose
    Georef georef;

    @SerializedName("hours")
    @Expose
    String hratencion;


    public Sucursal(String name, String address, String phone, Georef georef, String hratencion) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.georef = georef;
        this.hratencion = hratencion;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGeoref(Georef georef) {
        this.georef = georef;
    }

    public void setHratencion(String hratencion) {
        this.hratencion = hratencion;
    }



    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public LatLng getLocation(){return new LatLng(georef.getLat(),georef.getLon());}

    public String getPhone() {
        return phone;
    }

    public Georef getGeoref() {
        return georef;
    }

    public String getHratencion() {
        return hratencion;
    }



    protected Sucursal(Parcel in) {
        name = in.readString();
        address = in.readString();
        phone = in.readString();
        georef = (Georef) in.readValue(Georef.class.getClassLoader());
        hratencion = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(phone);
        dest.writeValue(georef);
        dest.writeString(hratencion);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Sucursal> CREATOR = new Parcelable.Creator<Sucursal>() {
        @Override
        public Sucursal createFromParcel(Parcel in) {
            return new Sucursal(in);
        }

        @Override
        public Sucursal[] newArray(int size) {
            return new Sucursal[size];
        }
    };
}