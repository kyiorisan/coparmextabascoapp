package com.android.coparmextab.UserInterface;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.coparmextab.R;
import com.android.utiles.DBManager;
import com.android.utiles.Empresa;
import com.android.utiles.Sucursal;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class   InfoEmpresaActivity extends Activity {

    //<editor-fold desc="Atributos">
    private Empresa empresa;


    private HashMap<String,Integer> imagenes;

    /**
     * TextView que contiene el nombre de la empresa.
     */
    private TextView NombreEmpresa;

    /**
     * TextView que contiene el logo de la empresa.
     */
    private ImageView LogoEmpresa;


    private TextView DireccionEmpresa;


    private ScrollViewCompatibleMapFragment mapa;
    /**
     * TextView que contiene el Número telefónico de la sucursal princial de la empresa.
     */
    private TextView TelefonoEmpresa;
    /**
     * TextView que contiene la dirección web de la empresa.
     */
    private TextView WebEmpresa;
    /**
     * TextView que contiene el email de la empresa.
     */
    private TextView EmailEmpresa;

    /**
     * Arreglo que contiene los layouts que se expanden.
     */

    private ExpandableLayout[] ListaEL = new ExpandableLayout[2];

    /**
     * Constante que contiene la llave asociada con el extra del intent.
     */
    private static final String EMPRESA_ARGUMENT = "EMPRESA";
    //</editor-fold>

    //<editor-fold desc="Ciclo de vida">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info_empresa);
        imagenes = new HashMap<>();
        imagenes.put("gol970314e77",R.drawable.gruasolmeca);
        imagenes.put("AEC960626I21",R.drawable.ado2);
        imagenes.put("BIM011108DJ5",R.drawable.bimbo2);
        imagenes.put("ACT6808066SA",R.drawable.tresguerras);
        imagenes.put("CAP821208LQ3",R.drawable.apasco2);
        imagenes.put("CCM010710UU1",R.drawable.cuauhtemoc2);
        imagenes.put("DRA041022VD8",R.drawable.dagdug2);
        imagenes.put("CFS950529980",R.drawable.union);
        imagenes.put("EPS950901R32",R.drawable.silva);
        imagenes.put("ETA050531TB7",R.drawable.exi);
        imagenes.put("FCM070911MM1",R.drawable.fcm);
        imagenes.put("FER941107H25",R.drawable.ferromax);
        imagenes.put("IIS070718V89",R.drawable.integrait);
        imagenes.put("LRT670904UA5",R.drawable.royal);
        imagenes.put("MCI011029MO8",R.drawable.chimay2);
        imagenes.put("ODO0010186B7",R.drawable.odonto);
        imagenes.put("PME110921NK6",R.drawable.petrofac);
        imagenes.put("PPL9202137A7",R.drawable.pintaplus);
        imagenes.put("PRO090121GN3",R.drawable.processa);
        imagenes.put("RSY0307231I4",R.drawable.radikal);
        imagenes.put("SEP02102FL3",R.drawable.uag2);
        imagenes.put("TEC030626BI9",R.drawable.tecno);
        imagenes.put("TNI8501249L9",R.drawable.todo);
        imagenes.put("UTM010711E2A",R.drawable.tecmi);
        imagenes.put("CCS0308152M5",R.drawable.comer);
        imagenes.put("CAH990125IFA",R.drawable.cahdez2);
        imagenes.put("COF010523BS9",R.drawable.cofisur2);
        imagenes.put("GCO1012042Y2",R.drawable.comsurlab2);
        imagenes.put("CAR851022AB6",R.drawable.conarechiga2);
        imagenes.put("CER050524D13",R.drawable.crinon);
        imagenes.put("CCS0308152M5",R.drawable.comer2);
        imagenes.put("CCL990108RCA",R.drawable.cuclaras2);
        imagenes.put("GCA0606269C4",R.drawable.gca);
        imagenes.put("GAP100211UC9",R.drawable.gaprosur);
        imagenes.put("GMA100512FV1",R.drawable.glamas);
        imagenes.put("ORG0903318x2",R.drawable.orgtec);
        imagenes.put("TCS050422KT9",R.drawable.tcs);
        imagenes.put("ZTI000926236",R.drawable.ztec);
        imagenes.put("APC070307CX8",R.drawable.apcc);
        imagenes.put("BMI9704113PA",R.drawable.bamon2);
        imagenes.put("BCH130306J17",R.drawable.bspch2);
        imagenes.put("CPN03013RH9",R.drawable.cpnd);
        imagenes.put("ECO110706CH4",R.drawable.editusco);
        imagenes.put("GSM1002269S1",R.drawable.gcservices);
        imagenes.put("GIC030613565",R.drawable.gicze);
        imagenes.put("IEE100923BJ8",R.drawable.iee);
        imagenes.put("LDA060111E32",R.drawable.labdias);
        imagenes.put("MAD120608RR3",R.drawable.mad);
        imagenes.put("MCO9709308E1",R.drawable.manpower);
        imagenes.put("MAA100914PS2",R.drawable.mmaa);
        imagenes.put("GUZA840602SK2",R.drawable.muvit);
        imagenes.put("ISR110125UN8",R.drawable.ram);
        imagenes.put("SOI121217HQ8",R.drawable.soilsolution);
        imagenes.put("TES0607059C6",R.drawable.tiest);
        imagenes.put("TM&070209N94",R.drawable.tmcompany);

        if (getIntent().getParcelableExtra(EMPRESA_ARGUMENT)!=null) {
            empresa = getIntent().getParcelableExtra(EMPRESA_ARGUMENT);
            final boolean empresaconsucursal = empresa.getSucursales().getSucursales().size() != 0;

            NombreEmpresa = (TextView) findViewById(R.id.nombreEmpresa);
            NombreEmpresa.setText(empresa.getName());
            LogoEmpresa = (ImageView) findViewById(R.id.infoempresaImagen);
            if(imagenes.get(empresa.getRfc())!=null){

                Picasso.with(this).load(imagenes.get(empresa.getRfc())).resize(300,150).centerInside().into(LogoEmpresa);
            }
            //establecer el logo de la empresa mediante piccaso.
            TelefonoEmpresa = (TextView) findViewById(R.id.telefono);
            TelefonoEmpresa.setText(empresaconsucursal?empresa.getSucursales().getSucursales().get(0).getPhone():"no disponible");
            EmailEmpresa = (TextView) findViewById(R.id.email);
            EmailEmpresa.setText(empresa.getEMAIL());
            WebEmpresa = (TextView) findViewById(R.id.webpage);
            WebEmpresa.setText(empresa.getWeb().equals("") ? "No cuenta con web disponible" : empresa.getWeb());
            DireccionEmpresa = (TextView) findViewById(R.id.direccion);
            DireccionEmpresa.setText(
                    empresaconsucursal?empresa.getSucursales().getSucursales().get(0).getAddress():"No disponible");
            ListaEL[0] = (ExpandableLayout) findViewById(R.id.exp_servicios);
            ListaEL[1] = (ExpandableLayout) findViewById(R.id.exp_sucursales);
            ListaEL[1].setExpanded(false);
            final View button_servicios = findViewById(R.id.tv_servicios);
            button_servicios.setTag(0);
            button_servicios.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandList((Integer) button_servicios.getTag());
                }
            });
            final View button_sucursales = findViewById(R.id.tv_sucursales);
            button_sucursales.setTag(1);
            button_sucursales.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandList((Integer) button_sucursales.getTag());
                }
            });


        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mapa = (ScrollViewCompatibleMapFragment) getFragmentManager().findFragmentById(R.id.mapita);
        final ScrollView sv = (ScrollView) findViewById(R.id.scroller);
        mapa.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                boolean empresaconsucursal = empresa.getSucursales().getSucursales().size() != 0;
                if(empresaconsucursal) {
                    for (Sucursal suc : empresa.getSucursales().getSucursales()
                            ) {
                       googleMap.addMarker(sucursalMarkerFactory(suc));
                    }
                }
                else {
                    LatLng location = new LatLng(17.997192, -92.951580);
                    googleMap.addMarker(new MarkerOptions().position(location)
                            .title("Matriz")
                            .snippet("allá muy lejos")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .visible(true));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
                }
            }
        });

        mapa.setListener(new ScrollViewCompatibleMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                sv.requestDisallowInterceptTouchEvent(true);
            }
        });
    }
    //</editor-fold>

    private MarkerOptions sucursalMarkerFactory(Sucursal suc){
        LatLng location = suc.getLocation();
        MarkerOptions mo =new MarkerOptions()
        .position(location)
        .title("Matriz")
        .snippet(suc.getAddress())
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        .visible(true);
        return mo;
    }

    public void expandList(int key) {
        switch (key) {
            case 0:
                ListaEL[0].toggle();
                break;
            case 1:
                ListaEL[1].toggle();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_empresa,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.favoritos){
            DBManager mydb = new DBManager(this);
            DBManager.insertEmpresaToDB(mydb,empresa);
            Toast.makeText(this,empresa.getName()+" agregada a favoritos",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
