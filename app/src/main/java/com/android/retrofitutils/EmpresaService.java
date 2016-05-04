package com.android.retrofitutils;

import com.android.utiles.JsonEmpresas;
import com.android.utiles.JsonRateResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by OscarEnrique on 13/01/2016.
 */
public interface EmpresaService {




    /**
     * lista sin filtrar de empresas.
     */

    @GET("patners.jsp?modname=empresas&q=")
    Call<JsonEmpresas>  empresas();

    /**
     * Lista de empresas por nombre.
     *
     * @param nombre el nombre de la empresa a buscar.
     * @return una lista de empresas cuyo nombre sea parecido a la empresa a buscar.
     */

    @GET("patners.jsp?modname=empresas")
    Call<JsonEmpresas>  buscarPorNombre(@Query("q") String nombre);


    /**
     *
     * @param id
     * @return
     */


    @GET("patners.jsp?modname=empresas")
    Call<JsonEmpresas> buscarPorId(@Query("id") String id);


    /**
     * Utilizado para subir una valoración a una empresa
     *
     * @param valor la calificación (0 positva,1 negativa).
     *
     * @param rfc la id de la empresa.
     *
     * @return
     * una Respuesta de la clase {@link JsonRateResponse}, la cual contiene un código de respuesta
     * y un mensaje de confirmación.
     */

    @GET ("rates.jsp?")
    Call<JsonRateResponse> valorarEmpresaAlt(@Query("q") String valor,@Query("id")String rfc);





    //<editor-fold desc="posibles consultas requeridas">
    /**
     * Lista de empresas por giro
     * @return una lista de empresas cuyo giro concuerda con el giro introducido.
     */


    /*
    @GET ("json/empresas.jsp?GIRO={giro}")
    Call<JsonEmpresas>  empresaPorGiro(@Query("giro") String giro);


    /**
     * Búsqueda segun servicios
     * @param servicio
     * @return


    @GET ("json/empresas.jsp?SERVICIOS={servicio}")
    Call<JsonEmpresas>  empresaPorServicio(@Query("servicio") String servicio);
    */
    //</editor-fold>


}
