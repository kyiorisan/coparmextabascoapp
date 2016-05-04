package com.android.utiles;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Valoraciones implements Parcelable {

    @SerializedName("ok")
    @Expose
    Integer OK;
    @SerializedName("bad")
    @Expose
    Integer BAD;

    public Valoraciones(){}

    public Valoraciones(Integer OK, Integer BAD) {
        this.OK = OK;
        this.BAD = BAD;
    }

    public Integer getOK() {
        return OK;
    }


    public void setOK(Integer OK) {
        this.OK = OK;
    }


    public Integer getBAD() {
        return BAD;
    }


    public void setBAD(Integer BAD) {
        this.BAD = BAD;
    }


    protected Valoraciones(Parcel in) {
        OK = in.readByte() == 0x00 ? null : in.readInt();
        BAD = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (OK == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(OK);
        }
        if (BAD == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(BAD);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Valoraciones> CREATOR = new Parcelable.Creator<Valoraciones>() {
        @Override
        public Valoraciones createFromParcel(Parcel in) {
            return new Valoraciones(in);
        }

        @Override
        public Valoraciones[] newArray(int size) {
            return new Valoraciones[size];
        }
    };
}
