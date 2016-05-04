package com.android.retrofitutils;

import com.android.utiles.JsonOfertas;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by OscarEnrique on 16/01/2016.
 */
public interface OfertasService {


    /**
     * Obtiene todas las ofertas en el servidor, usado para propósitos de testeo.
     */
    @GET("json/ofertas.jsp")
    Call<JsonOfertas> getOfertas();

    /**
     * Realiza una búsqueda de ofertas según el nombre de la empresa a la que pertenece las oferta
     *
     * @param NombreEmpresa
     * El nombre de la empresa a buscar.
     * @return
     * Lista de ofertas que pertenezcan a la empresa introducida.
     */
    @GET("json/ofertas.jsp?RFC={empresa}")
    Call<JsonOfertas> getOfertasPorEmpresa(@Query("empresa") String NombreEmpresa);


    /**
     * Obtiene las útimas ofertas hasta una fecha mínima límite(esto puede cambiar según la implementación
     * en el servidor) o vigencia.
     *
     * @param AntiguedadMinima
     * @return
     */
    @GET("json/ofertas.jsp?FINI={AntiguedadMinima}OrderBy=IsDe10,OrderBy=FEND")
    Call<JsonOfertas> getOfertasUpdates(@Query("AntiguedadMinima") String AntiguedadMinima);


}
