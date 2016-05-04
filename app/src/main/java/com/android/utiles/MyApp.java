package com.android.utiles;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Clase utilizada para acceder a recursos de aplicación sin tener que proveer y almacenar un context,
 * cualquier llamado o necesidad de recursos en clases que no contienen un Context, deberán
 * utilizar {@link #getStaticResources()}.
 *
 * Created by OscarEnrique on 13/01/2016.
 */
public class MyApp extends Application {
    private static Context ctx;

    public static Resources getStaticResources(){
        return ctx.getResources();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = getApplicationContext();
    }
}
