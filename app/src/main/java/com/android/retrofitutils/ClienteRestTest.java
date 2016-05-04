package com.android.retrofitutils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by kike on 1/05/16.
 */
public class ClienteRestTest {
    private static final String base_url = "http://192.168.1.65:8080/";
    private static final String gseta_url = "http://192.168.112.207:8080/";

    private static EmpresaTestService empresaservice;
    static{
        setupClient();
    }

    private static void setupClient(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
        OkHttpClient cliente = new OkHttpClient()
                .newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(gseta_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(cliente)
                .build();
        empresaservice = retrofit.create(EmpresaTestService.class);
    }

    public static EmpresaTestService getEmpresaservice() {
        return empresaservice;
    }
}
