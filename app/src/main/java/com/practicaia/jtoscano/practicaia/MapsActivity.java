package com.practicaia.jtoscano.practicaia;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        String Latitud= new String();
        String Longitud= new String();
        String Nombre= new String();
        String Telefono= new String();
        String Direccion= new String();
        String Url= new String();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("latitud")) {
            Latitud = intent.getStringExtra("latitud");
        }
        if (intent != null && intent.hasExtra("longitud")) {
            Longitud = intent.getStringExtra("longitud");
        }
        if (intent != null && intent.hasExtra("nombre")) {
            Nombre = intent.getStringExtra("nombre");
        }
        if (intent != null && intent.hasExtra("telefono")) {
            Telefono = intent.getStringExtra("telefono");
        }
        if (intent != null && intent.hasExtra("direccion")) {
            Direccion = intent.getStringExtra("direccion");
        }
        if (intent != null && intent.hasExtra("url")) {
            Url = intent.getStringExtra("url");
        }
        Double latitud = Double.parseDouble(Latitud);
        Double longitud = Double.parseDouble(Longitud);

        LatLng marcador= new LatLng(latitud, longitud);

        mMap.addMarker(new MarkerOptions()
                .position(marcador)
                .title(Nombre)
                .snippet(Direccion + "\n" + Telefono + "\n" + Url)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_ubicacion)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marcador, 16));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(MapsActivity.this);
                info.setOrientation(LinearLayout.VERTICAL);

                ImageView markerIcon = new ImageView(MapsActivity.this);
                markerIcon.setImageResource(R.drawable.icono_cinepolis);

                TextView title = new TextView(MapsActivity.this);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(MapsActivity.this);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(markerIcon);
                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }
}
