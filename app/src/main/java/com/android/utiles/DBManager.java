package com.android.utiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    public SQLiteDatabase DB;
    public static String DBPath;
    public static String DBName = "coparmexappfavs.db";
    public static final int DB_VERSION = 1;
    public static Context context;


    public DBManager(Context context) {
        super(context, DBName, null, DB_VERSION);
        this.context = context;
        DBPath = "/data/data/" + context.getPackageName() + "/databases";
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Inicializador de db.
        Log.i("SQLManager.onCreate: ", "Inicializando la base de datos....");
        Log.i("SQLManager.onCreate: ", "created database " + getDatabaseName());
        Log.i("SQLManager.onCreate: ", "Path - " + db.getPath());
        try {
            InputStream myinput = context.getAssets().open("sqlstruct.txt");
            InputStreamReader inputreader = new InputStreamReader(myinput);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String statement;
            while ((statement = buffreader.readLine()) != null) {
                db.execSQL(statement);
                Log.d("Ejecutando: ", statement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public static boolean updateEmpresa(DBManager db, Empresa empresa) {
        //conexión a la base de datos
        SQLiteDatabase SQDB = db.getWritableDatabase();
        //objeto que contiene los valores a modificar
        ContentValues cv = new ContentValues();
        //clausula where para seleccionar la tupla.
        String wherefilter = EmpresaTableInfo.RFC + "=?";
        String args[] = {empresa.getRfc()};
        int res = 0;
        Empresa oldempresa = DBManager.getEmpresaByKey(db, empresa.getRfc());
        if (empresa.equals(oldempresa)) {
            return false;
        } else {
            //Establecemos valores a modificar
            cv.put(EmpresaTableInfo.NOMBRE, empresa.getName());
            cv.put(EmpresaTableInfo.GIRO, empresa.getGiro());
            cv.put(EmpresaTableInfo.LOGO, empresa.getFoto());
            cv.put(EmpresaTableInfo.WEB, empresa.getWeb());
            cv.put(EmpresaTableInfo.EMAIL, empresa.getEMAIL());
            cv.put(EmpresaTableInfo.VALUE_OK, empresa.getVALORACIONES().getOK());
            cv.put(EmpresaTableInfo.VALUE_BAD, empresa.getVALORACIONES().getBAD());
            cv.put(EmpresaTableInfo.ES_DE_10, empresa.isDe10());
            //actualización de la tupla en la base de datos
            res = SQDB.update(EmpresaTableInfo.TABLE_NAME, cv, wherefilter, args);
            //retorna true en caso de haber logrado hacer update.
            return res > 0;
        }
    }

    public static boolean updateSucursalesEmpresa(DBManager db, Empresa empresa) {
        List<Sucursal> oldsucursales = DBManager.getSucursalesByEmpresa(db, empresa);
        if (oldsucursales.equals(empresa.getSucursales().getSucursales())) {
            Log.i("SQLDatabase", "no hay cambios en sucurusales de " + empresa.getRfc());
            return false;
        } else {
            //liberar memoria.
            oldsucursales.removeAll(oldsucursales);
            //se borran todas las sucursales que tenga la empresa.
            deleteSucursalesByEmpresa(db, empresa);
            /*se vuelven a agregar todas las sucursales que tenga la empresa, esto con el objetivo de actualizar incluso
             si se llegaran a tener más sucursales que en el momento en que se agregó la empresa a favoritos.
             */
            insertSucursalesByEmpresa(db, empresa);
            return true;
        }
    }


    public static boolean checkDbExists() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DBPath + DBName;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // database does't exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    public static boolean insertEmpresaToDB(DBManager db, Empresa empresa) {
        SQLiteDatabase SQDB = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EmpresaTableInfo.RFC, empresa.getRfc());
        cv.put(EmpresaTableInfo.NOMBRE, empresa.getName());
        cv.put(EmpresaTableInfo.GIRO, empresa.getGiro());
        cv.put(EmpresaTableInfo.LOGO, empresa.getFoto());
        cv.put(EmpresaTableInfo.WEB, empresa.getWeb());
        cv.put(EmpresaTableInfo.EMAIL, empresa.getEMAIL());
        cv.put(EmpresaTableInfo.VALUE_OK, empresa.getVALORACIONES().getOK());
        cv.put(EmpresaTableInfo.VALUE_BAD, empresa.getVALORACIONES().getBAD());
        cv.put(EmpresaTableInfo.ES_DE_10, empresa.de10);
        long result = SQDB.insert(EmpresaTableInfo.TABLE_NAME, null, cv);
        if (!insertSucursalesByEmpresa(db, empresa)) {
            return false;
        }
        if (result != -1) {
            Log.i("Database:", "datos agregados exitosamente");
            return true;
        } else {
            Log.i("Database", "Ocurrió un error....");
            return false;
        }

    }

    public static boolean insertSucursalesByEmpresa(DBManager db, Empresa empresa) {
        SQLiteDatabase SQDB = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int x = 1;
        for (Sucursal sucursal :
                empresa.getSucursales().getSucursales()) {
            cv.put(SucursalTableInfo.ID, sucursal.getName());
            cv.put(SucursalTableInfo.NAME, x == 1 ? "Matriz" : "Sucursal " + String.valueOf(x));
            cv.put(SucursalTableInfo.ADDRESS, sucursal.getAddress());
            cv.put(SucursalTableInfo.PHONE, sucursal.getPhone());
            cv.put(SucursalTableInfo.LATITUDE, sucursal.getGeoref().getLat());
            cv.put(SucursalTableInfo.LONGITUDE, sucursal.getGeoref().getLon());
            cv.put(SucursalTableInfo.HORA_ATENCION, sucursal.getHratencion());
            cv.put(SucursalTableInfo.EMPRESA_ID, empresa.getRfc());
            long result = SQDB.insert(SucursalTableInfo.TABLE_NAME, null, cv);
            if (result != -1) {
                Log.d("DATABASE: ", "Agregada sucursal" + cv.get(SucursalTableInfo.ID));
            } else {
                return false;
            }
        }
        return true;
    }


    /**
     * Devuelve una lista de sucursales que pertenecen a la empresa introducida pasada como
     * argumento.
     *
     * @param db      una instancia de DBManager que contenga la base de datos de la app.
     * @param empresa La empresa de la cual se desean obtener sus sucursales.
     * @return Lista de sucursales de la empresa que fue introducida.
     */

    public static List<Sucursal> getSucursalesByEmpresa(DBManager db, Empresa empresa) {
        SQLiteDatabase SQ = db.getReadableDatabase();
        //Se establecen las columnas
        String[] columns = {SucursalTableInfo.ID, SucursalTableInfo.NAME, SucursalTableInfo.ADDRESS,
                SucursalTableInfo.PHONE, SucursalTableInfo.LATITUDE, SucursalTableInfo.LONGITUDE, SucursalTableInfo.HORA_ATENCION,
                SucursalTableInfo.EMPRESA_ID};
        // se crea el query de seleccion
        String selection = SucursalTableInfo.EMPRESA_ID + "=?";
        String[] args = {empresa.getRfc()};
        Cursor cursor = SQ.query(SucursalTableInfo.TABLE_NAME, columns, selection, args, null, null, null, null);
        ArrayList<Sucursal> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                list.add(new Sucursal(cursor.getString(0), cursor.getString(2), cursor.getString(3),
                        new Georef(cursor.getDouble(4), cursor.getDouble(5)), cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        return list;
    }


    public static boolean deleteEmpresaFavorites(DBManager db, String empresaRFC) {
        SQLiteDatabase SQ = db.getWritableDatabase();
        Empresa empresa = getEmpresaByKey(db, empresaRFC);
        String[] args = {empresa.getRfc()};
        //Primero se borran las sucursales.
        deleteSucursalesByEmpresa(db, empresa);
        int result = SQ.delete(EmpresaTableInfo.TABLE_NAME, EmpresaTableInfo.RFC + "=?", args);
        if (result > 0) {
            Log.i("SQLiteDatabase", "Eliminada " + empresa.getName() + " de los favoritos.");
            return true;

        } else {
            Log.e("SQLiteDatabase", "No se pudo elimar la empresa");
            return false;
        }

    }

    public static Empresa getEmpresaByKey(DBManager db, String RFC) {
        SQLiteDatabase SQL = db.getReadableDatabase();
        String[] columns = {EmpresaTableInfo.RFC, EmpresaTableInfo.NOMBRE, EmpresaTableInfo.GIRO, EmpresaTableInfo.LOGO,
                EmpresaTableInfo.WEB, EmpresaTableInfo.EMAIL, EmpresaTableInfo.VALUE_OK, EmpresaTableInfo.VALUE_BAD, EmpresaTableInfo.ES_DE_10};
        String[] args = {RFC};
        Cursor cursor = SQL.query(EmpresaTableInfo.TABLE_NAME, columns, EmpresaTableInfo.RFC + "=?", args, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            Empresa e = new Empresa(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)
                    , cursor.getString(4), cursor.getString(5), new Valoraciones(cursor.getInt(6), cursor.getInt(7))
                    , cursor.getInt(8), new JsonSucursales(1, getSucursalesByEmpresa(db, cursor.getColumnName(0))));
            return e;
        } else {
            Log.e("SQLiteDatabase", "no se encontró la empresa.");
            return null;
        }

    }


    public static boolean deleteSucursalesByEmpresa(DBManager db, Empresa empresa) {
        SQLiteDatabase SQ = db.getWritableDatabase();
        String[] args = {empresa.getRfc()};
        int result = SQ.delete(SucursalTableInfo.TABLE_NAME, SucursalTableInfo.EMPRESA_ID + "=?", args);
        if (result > 0) {
            Log.i("SQLDatabase", "Eliminados " + result + " registros");
            return true;
        } else {
            Log.i("SQLDatabase", "No hay sucursales para borrar");
            return false;
        }
    }

    public static List<Sucursal> getSucursalesByEmpresa(DBManager db, String empresaRFC) {
        SQLiteDatabase SQ = db.getReadableDatabase();
        String[] columns = {SucursalTableInfo.ID, SucursalTableInfo.NAME, SucursalTableInfo.ADDRESS,
                SucursalTableInfo.PHONE, SucursalTableInfo.LATITUDE, SucursalTableInfo.LONGITUDE, SucursalTableInfo.HORA_ATENCION,
                SucursalTableInfo.EMPRESA_ID};
        String selection = SucursalTableInfo.EMPRESA_ID + "=?";
        String[] args = {empresaRFC};
        Cursor cursor = SQ.query(SucursalTableInfo.TABLE_NAME, columns, selection, args, null, null, null, null);
        ArrayList<Sucursal> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                list.add(new Sucursal(cursor.getString(0), cursor.getString(2), cursor.getString(3),
                        new Georef(cursor.getDouble(4), cursor.getDouble(5)), cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public static List<Empresa> getEmpresasFavorites(DBManager db) {
        SQLiteDatabase SQDB = db.getReadableDatabase();
        String[] columns = {EmpresaTableInfo.RFC, EmpresaTableInfo.NOMBRE, EmpresaTableInfo.GIRO, EmpresaTableInfo.LOGO,
                EmpresaTableInfo.WEB, EmpresaTableInfo.EMAIL, EmpresaTableInfo.VALUE_OK, EmpresaTableInfo.VALUE_BAD, EmpresaTableInfo.ES_DE_10};
        Cursor cursor = SQDB.query(EmpresaTableInfo.TABLE_NAME, columns, null, null, null, null, EmpresaTableInfo.ES_DE_10 + " DESC," + EmpresaTableInfo.VALUE_OK + " DESC, "
                + EmpresaTableInfo.NOMBRE + " ASC");
        ArrayList<Empresa> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                list.add(new Empresa(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)
                        , cursor.getString(4), cursor.getString(5), new Valoraciones(cursor.getInt(6), cursor.getInt(7))
                        , cursor.getInt(8), new JsonSucursales(1, getSucursalesByEmpresa(db, cursor.getColumnName(0)))));
            } while (cursor.moveToNext());
        }
        return list;
    }

    //region Info de Tablas

    /**
     * Clase que contiene la información básica de la tabla de Empresas.
     */
    public static abstract class EmpresaTableInfo implements BaseColumns {

        public static final String TABLE_NAME = "EMPRESAS";
        public static final String RFC = "RFC";
        public static final String NOMBRE = "NOMBRE";
        public static final String GIRO = "GIRO";
        public static final String LOGO = "LOGO";
        public static final String WEB = "WEB";
        public static final String EMAIL = "EMAIL";
        public static final String VALUE_OK = "VALOK";
        public static final String VALUE_BAD = "VALBAD";
        public static final String ES_DE_10 = "ISDEDIEZ";
        public static final String SERVICIOS = "SERVICIOS";
    }

    /**
     * Clase que contiene la información básica de la tabla de Sucursal
     */
    public static abstract class SucursalTableInfo implements BaseColumns {

        public static final String TABLE_NAME = "SUCURSALES";
        public static final String ID = "ID";
        public static final String NAME = "NAME";
        public static final String ADDRESS = "ADDRESS";
        public static final String PHONE = "PHONE";
        public static final String LATITUDE = "LAT";
        public static final String LONGITUDE = "LON";
        public static final String HORA_ATENCION = "HRATENCION";
        public static final String EMPRESA_ID = "EMP_RFC";
    }

    /**
     * Esta clase contiene la información básica de la tabla de ofertas.
     */

    public static abstract class OfertaTableInfo implements BaseColumns {
        public static final String TABLE_NAME = "OFERTAS";
        public static final String ID = "ID";
        public static final String TEXT = "TEXT";
        public static final String BANNER = "BANNER";
        public static final String FECHA_INICIO = "FINI";
        public static final String FECHA_EXPIRACION = "FEND";
        public static final String INFO_WEB = "WEBSRCS";
        public static final String EMPRESA_ID = "EMP_RFC";
    }
    //endregion


}
