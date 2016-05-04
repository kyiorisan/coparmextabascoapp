package com.android.utiles;

import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Clase que contiene a la entidad de la Tabla Empresas de la BD
 * además de incorporar anotaciones para transformación automática ç
 * en parcel (no ha sido implementado todavía).
 * <p/>
 * Created by OscarEnrique on 04/12/2015.
 */
public class Empresa implements Parcelable {

    //region Atributos
    /**
     * Rfc de la empresa, usado como clave primaria para dierenciación de empresas
     */
    @SerializedName("rfc")
    @Expose
    String Rfc;


    /**
     * Nombre de la empresa, el nombre común con el que se le conoce a la empresa.
     */
    @SerializedName("name")
    @Expose
    String Name;


    /**
     * Giro de la empresa, el sector empresarial al que se dedica la empresa.
     */
    @SerializedName("activity")
    @Expose
    String Giro;


    /**
     * El logo o la foto de la empresa.
     */
    @SerializedName("logo")
    public String Foto;


    /**
     * Direccion o URL de la web de la empresa.
     */
    @SerializedName("web")
    @Expose
    String Web;


    /**
     * Correo electrónico para comunicarse con la empresa.
     */
    @SerializedName("email")
    @Expose
    String EMAIL;


    /**
     * Valoraciones o calificaciones que ha recibido la empresa.
     */
    @SerializedName("rates")
    @Expose
    Valoraciones VALORACIONES;



    /**
     * Certificado de ser empresa de 10.
     */
    @SerializedName("is")
    @Expose
    int de10;


    /**
     * Sucursales de la empresa, esta clase contiene dos valores, el primero un número total de
     * sucursales y el segundo una lista con cada una de las sucursales y sus respectivos
     * atributos.
     */

    @SerializedName("branchs")
    @Expose
    JsonSucursales sucursales;
    //endregion

    //region Constructores
    public Empresa() {
    }

    /**
     * Constructor utilizado por Gson Para hacer el parsing desde el Json.
     * @param rfc
     * @param name
     * @param giro
     * @param foto
     * @param web
     * @param EMAIL
     * @param VALORACIONES
     * @param de10
     * @param sucursales
     */

    public Empresa(String rfc, String name, String giro, String foto, String web, String EMAIL, Valoraciones VALORACIONES, int de10, JsonSucursales sucursales) {
        Rfc = rfc;
        Name = name;
        Giro = giro;
        Foto = foto;
        Web = web;
        this.EMAIL = EMAIL;
        this.VALORACIONES = VALORACIONES;
        this.de10 = de10;
        this.sucursales = sucursales;
    }
    //endregion

    //region Getters
    /**
     * Obtiene el rfc de la empresa.
     * @return un {@link String} con el rfc de la empresa.
     */
    public String getRfc() {
        return Rfc;
    }

    /**
     * Obtiene el nombre con el que se le conoce a la empresa.
     *
     * @return
     * un {@link String} con el el nombre de la empresa.
     */

    public String getName() {
        return Name;
    }

    /**
     * Obtiene el giro o actividades que realiza la empresa.
     * @return
     * un {@link String} con el giro de la empresa.
     */

    public String getGiro() {
        return Giro;
    }

    /**
     * Obtiene el url dónde se encuentra alojado el logo de la empresa.
     *
     * @return
     * un {@link String} con la dirección al donde se encuentra el lógo de la empresa.
     */
    public String getFoto() {
        return Foto;
    }

    /**
     * Obtiene la url de la web de la empresa.
     * @return
     * un {@link String} con la url de la empresa.
     */
    public String getWeb() {
        return Web;
    }

    /**
     * Obtiene la dirección de correo electrónico de la empresa.
     *
     * @return
     * un {@link String} con la dirección de correo de la empresa.
     */

    public String getEMAIL() {
        return EMAIL;
    }

    /**
     * Obtiene las valoraciones dadas por los usuarios a la empresa.
     * @return
     * un objeto de la clase {@link Valoraciones} representando las calificaciones que
     * ha recibido la empresa de los usuarios.
     */

    public Valoraciones getVALORACIONES() {
        return VALORACIONES;
    }

    /**
     * Obtiene si la empresa es una empresa de 10 o un socio coparmex sencillo.
     *
     * @return
     * {@code true} si el atributo {@link #de10} tiene el valor de 1.
     *
     */
    public boolean isDe10() {
        return de10 == 1;
    }


    /**
     * Obtiene las sucursales que pertenecn a la empresa.
     *
     * @return
     * Un objeto de la clase{@link JsonSucursales} con las sucursales de la empresa.
     *
     */
    public JsonSucursales getSucursales() {
        return sucursales;
    }
    //endregion

    //region Setters
    /**
     * Establece el rfc de la empresa.
     *
     * @param rfc el rfc
     */
    public void setRfc(String rfc) {
        Rfc = rfc;
    }

    /**
     * Establece el nombre de la empresa.
     * @param name el nombre
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Establece el giro de la empresa.
     * @param giro el giro.
     */

    public void setGiro(String giro) {
        Giro = giro;
    }

    /**
     * Establece la url del logo del empresa.
     * @param foto la {@link String} de texto con la url de la empresa.
     */

    public void setFoto(String foto) {
        Foto = foto;
    }

    /**
     * Establece la direccion del sitio web de la empresa.
     * @param web la {@link String} de texto con la url del sitio web.
     */

    public void setWeb(String web) {
        Web = web;
    }

    /**
     * Establece la direccion de correo electronico de la empresa.
     * @param EMAIL la {@link String} de texto con la direccion de correo electronico.
     */

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    /**
     * Establece las valoraciones de la empresa.
     * @param VALORACIONES un objeto de la clase {@link Valoraciones} con las valoraciones de la empresa.
     */

    public void setVALORACIONES(Valoraciones VALORACIONES) {
        this.VALORACIONES = VALORACIONES;
    }

    /**
     * Establece si la empresa es de 10.
     * @param isde10 1 si la empresa es de 10.
     */

    public void setIsde10(int isde10) {
        de10 = isde10;
    }

    /**
     * Establece las sucursales que pertenecen a la empresa.
     * @param sucursales un ojbeto de la clase {@link JsonSucursales} que contiene las sucursales de la empresa.
     */
    public void setSucursales(JsonSucursales sucursales) {
        this.sucursales = sucursales;
    }
    //endregion

    //region Bases de datos de favoritos.


    /**
     * Extrae una lista con todas las Empresas de la base de datos de favoritos.
     *
     * @return Una lista de {@link java.util.ArrayList}&lt<code>Empresa</code>&gt con los resultados de la consulta
     * introducida.
     */
    public static List<Empresa> getFavoritesFromDB(SQLiteDatabase db) {
        List<Empresa> list = null;

        return list;
    }

    /**
     * Permite agregar una empresa a la base de datos de empresas favoritas.
     *
     * @param db Base de datos a la cual agregar la empesa.
     *
     *
     * @param emp Empresa a ser agregada.
     *
     */
    public static void addFavoriteToDB(SQLiteDatabase db,Empresa emp){
        //implementación
    }
    //endregion

    //region Implementación de Parcelable.
    protected Empresa(Parcel in) {
        Rfc = in.readString();
        Name = in.readString();
        Giro = in.readString();
        Foto = in.readString();
        Web = in.readString();
        EMAIL = in.readString();
        VALORACIONES = (Valoraciones) in.readValue(Valoraciones.class.getClassLoader());
        de10 = in.readInt();
        sucursales = (JsonSucursales) in.readValue(JsonSucursales.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Rfc);
        dest.writeString(Name);
        dest.writeString(Giro);
        dest.writeString(Foto);
        dest.writeString(Web);
        dest.writeString(EMAIL);
        dest.writeValue(VALORACIONES);
        dest.writeInt(de10);
        dest.writeValue(sucursales);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Empresa> CREATOR = new Parcelable.Creator<Empresa>() {
        @Override
        public Empresa createFromParcel(Parcel in) {
            return new Empresa(in);
        }

        @Override
        public Empresa[] newArray(int size) {
            return new Empresa[size];
        }
    };

    //endregion
}
