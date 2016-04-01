package com.engo551.plars.app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private RestaurantDatabaseHelper rDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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
        map = googleMap;

        final LatLng CALGARY = new LatLng(51.0486, -114.0708);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(CALGARY, 10));

        rDb = new RestaurantDatabaseHelper(this);
        SQLiteDatabase db = rDb.getReadableDatabase();

        String[] columns = {"ID", "NAME", "Latitude", "Longitude"};
        Cursor cursor = db.query("restaurants", columns, null, null, null, null, null);

        try {
            while (cursor.moveToNext()) {
                LatLng latLng = new LatLng(cursor.getDouble(2), cursor.getDouble(3));
                map.addMarker(new MarkerOptions().position(latLng).title(cursor.getString(1)));

                //TODO: Center on current user location
                //TODO: Draw marker according to type
                //TODO: Add info window click event
            }
        }
        finally {
            cursor.close();
        }
    }
}
