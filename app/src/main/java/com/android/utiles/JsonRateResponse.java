package com.android.utiles;

import com.google.gson.annotations.SerializedName;

/**
 * Esta clase es utilizada para mapear con Gson una respuesta del servidor al realizar una valoración.
 *
 * Created by OscarEnrique on 28/01/2016.
 */
public class JsonRateResponse {

    @SerializedName("code")
    String codigo;

    @SerializedName("msg")
    String msg;

    public JsonRateResponse(String codigo, String msg) {
        this.codigo = codigo;
        this.msg = msg;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
