package com.example.truckpark.view.functionality.navigation;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import com.example.truckpark.R;
import com.example.truckpark.service.location.LocationDeviceService;
import com.example.truckpark.service.route.SimpleRouteService;
import com.example.truckpark.view.functionality.location.MapsActivityLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent intent =getIntent();
        String src = intent.getStringExtra(SRC);
        String dst = intent.getStringExtra(DST);
        mMap = googleMap;

        SimpleRouteService simpleRouteService = new SimpleRouteService();

        //THINK ABOUT IT LATER,async attitute would be better here in future
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ///////////////////////////////////////////////////////////////////////////////////////////

        List<Double[]> routeCoordinates =  simpleRouteService.getSimpleRoute(src, dst, getApplicationContext());

        PolylineOptions rectOptions = new PolylineOptions();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        routeCoordinates.forEach(coordinatesPair -> {
            LatLng latLng = new LatLng(coordinatesPair[0],coordinatesPair[1]);
            builder.include(latLng);
            rectOptions.add(latLng);
        });

        LatLngBounds latLngBounds = builder.build();

        Polyline polyline = mMap.addPolyline(rectOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds,20));

        
//        if (LocationDeviceService.lastLocation != null && LocationDeviceService.mCurrLocationMarker==null){
//            LocationDeviceService.mCurrLocationMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(LocationDeviceService.lastLocation.getLatitude(), LocationDeviceService.lastLocation.getLongitude())).title("Truck Position"));
//        }

    }
}
