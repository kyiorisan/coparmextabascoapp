package com.android.retrofitutils;

import com.android.utiles.JsonEmpresas;
import com.android.utiles.JsonRateResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kike on 1/05/16.
 */
public interface EmpresaTestService {
    /**
     * Obtiene todas las empresas del archivo js bajado del servidor original.
     * @return Objeto de la clase JsonEmpresas.
     */
    @GET("server/partners.jsp")
    Call<JsonEmpresas> getEmpresas();
}
