package com.android.coparmextab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.coparmextab.ListaEmpresas.MyEmpresaRecyclerViewAdapter;
import com.android.coparmextab.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Esta actividad se encarga de mostrar el punto donde se encuentra la empresa. Esta
 * es llamada al dar click en el ícono (ImageView) de localización que se encuentra en
 * {@link MyEmpresaRecyclerViewAdapter.EmpresaViewHolder} llamado
 * {@ mMapView}
 */

public class MapsActivity extends Activity implements OnMapReadyCallback {


    /**
     * El mapa que se utilizará
     */
    private GoogleMap mMap;

    /**
     * La GeoPosición de la empresa.
     */
    private LatLng location;

    /**
     * El nombre de la empresa, utilizado al dar click en el marcador de
     * posición
     */
    private String name;

    /**
     * La dirección de la empresa mostrada al dar click en el marcador de
     * posición.
     */
    private String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        if (savedInstanceState != null) {
            location = new LatLng(savedInstanceState.getDouble("lat"),
                    savedInstanceState.getDouble("lng"));
            name = savedInstanceState.getString("name");
            address = savedInstanceState.getString("dir");
        }
        Intent intent = getIntent();
        location = new LatLng(intent.getDoubleExtra("lat", 1),
                intent.getDoubleExtra("lng", 1));
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("dir");
        Toast.makeText(this,"Lat: "+location.latitude+" lng: "+location.longitude,Toast.LENGTH_SHORT).show();
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        Marker marker = mMap.addMarker(new MarkerOptions().position(location)
                .title(name)
                .snippet(address)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .visible(true));



        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,17));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17),2000,null);
/*       final View mapView = getFragmentManager().findFragmentById(R.id.map).getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressLint("NewApi")
                // We check which build version we are using.
                @Override
                public void onGlobalLayout() {
                    LatLngBounds bounds = new LatLngBounds.Builder().include(location).build();
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                }
            });
        }*/
    }
}
