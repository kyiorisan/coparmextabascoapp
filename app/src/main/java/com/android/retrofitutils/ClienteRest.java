package com.android.retrofitutils;


import com.android.coparmextab.R;
import com.android.utiles.MyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by OscarEnrique on 13/01/2016.
 */
public class ClienteRest {


    //<editor-fold desc="Atributos">

    private static final String base_2 = MyApp.getStaticResources().getString(R.string.webpage);

    private static final String ip = MyApp.getStaticResources().getString(R.string.directip);

    private static EmpresaService empresaservice;

    private static OfertasService ofertaService;
    //</editor-fold>

    //<editor-fold desc="Inicialización">
    static{
        setupClient();
    }


    private static void setupClient(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(2,TimeUnit.SECONDS)
                .build();
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(ip)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        empresaservice = retrofit.create(EmpresaService.class);
        ofertaService = retrofit.create(OfertasService.class);
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public static EmpresaService getEmpresaservice() {
        return empresaservice;
    }

    public static OfertasService getOfertaservice(){ return ofertaService;}
    //</editor-fold>


}








