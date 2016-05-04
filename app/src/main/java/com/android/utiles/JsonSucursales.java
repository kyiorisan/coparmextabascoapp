package com.android.utiles;

import android.os.Parcel;
import java.util.ArrayList;
import android.os.Parcelable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OscarEnrique on 13/01/2016.
 */

public class JsonSucursales implements Parcelable {


    @SerializedName("total")
    @Expose
    Integer Total;



    @SerializedName("rows")
    @Expose
    List<Sucursal> sucursales;

    public JsonSucursales(){}


    public JsonSucursales(Integer total, List<Sucursal> sucursales) {
        Total = total;
        this.sucursales = sucursales;
    }

    public Integer getTotal() {
        return Total;
    }


    public void setTotal(Integer total) {
        this.Total = total;
    }


    public List<Sucursal> getSucursales() {
        return sucursales;
    }


    public void setSucursals(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }


    protected JsonSucursales(Parcel in) {
        Total = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            sucursales = new ArrayList<Sucursal>();
            in.readList(sucursales, Sucursal.class.getClassLoader());
        } else {
            sucursales = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Total == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(Total);
        }
        if (sucursales == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(sucursales);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<JsonSucursales> CREATOR = new Parcelable.Creator<JsonSucursales>() {
        @Override
        public JsonSucursales createFromParcel(Parcel in) {
            return new JsonSucursales(in);
        }

        @Override
        public JsonSucursales[] newArray(int size) {
            return new JsonSucursales[size];
        }
    };
}
