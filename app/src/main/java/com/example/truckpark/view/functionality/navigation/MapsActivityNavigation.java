package com.example.truckpark.view.functionality.navigation;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.truckpark.R;
import com.example.truckpark.service.route.SimpleRouteService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class MapsActivityNavigation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static final String SRC = "src";
    public static final String DST = "dst";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_navigation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent =getIntent();
        String src = intent.getStringExtra(SRC);
        String dst = intent.getStringExtra(DST);


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
        Intent intent =getIntent();
        String src = intent.getStringExtra(SRC);
        String dst = intent.getStringExtra(DST);
        mMap = googleMap;

//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        SimpleRouteService simpleRouteService = new SimpleRouteService();

        List<Double[]> routeCoordinates =  simpleRouteService.getSimpleRoute(src, dst, getApplicationContext());

        // Instantiates a new Polyline object and adds points to define a rectangle
        PolylineOptions rectOptions = new PolylineOptions();

        routeCoordinates.forEach(coordinatesPair -> {
            LatLng latLng = new LatLng(coordinatesPair[0],coordinatesPair[1]);
            rectOptions.add(latLng);
        });

// Get back the mutable Polyline
        Polyline polyline = mMap.addPolyline(rectOptions);

    }
}
