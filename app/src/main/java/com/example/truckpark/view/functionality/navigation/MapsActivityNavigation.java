package com.example.truckpark.view.functionality.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import androidx.fragment.app.FragmentActivity;

import com.example.truckpark.R;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.repository.CurrentMops;
import com.example.truckpark.service.route.SimpleRouteService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivityNavigation extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap mMap;
    public static final String SRC = "src";
    public static final String DST = "dst";

    private List<Mop> allMops;
    private List<MarkerOptions> markersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_navigation);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();

        //THINK ABOUT IT LATER,async attitute would be better here in future
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.allMops = CurrentMops.getCurrentMopsInstance().getCurrentMopsList();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent intent = getIntent();
        String src = intent.getStringExtra(SRC);
        String dst = intent.getStringExtra(DST);
        mMap = googleMap;

        SimpleRouteService simpleRouteService = new SimpleRouteService();


        List<Double[]> routeCoordinates = simpleRouteService.getSimpleRoute(src, dst, getApplicationContext());

        PolylineOptions rectOptions = new PolylineOptions();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        routeCoordinates.forEach(coordinatesPair -> {
            LatLng latLng = new LatLng(coordinatesPair[0], coordinatesPair[1]);
            builder.include(latLng);
            rectOptions.add(latLng);
        });

        LatLngBounds latLngBounds = builder.build();

        Polyline polyline = mMap.addPolyline(rectOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 20));


        mMap.setMyLocationEnabled(true);

        clearAndAddMarkers();

    }

    private void clearAndAddMarkers() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (mMap != null) {
                    mMap.clear();
                    allMops = CurrentMops.getCurrentMopsInstance().getCurrentMopsList();
                    markersList = new ArrayList<>();
                    addMarkersToMap();
                }

                handler.postDelayed(this, 5000);
            }
        });
    }

    private void addMarkersToMap() {
        allMops.forEach(mop -> markersList.add(new MarkerOptions()
                .position(new LatLng(mop.getCoordinate().getX(), mop.getCoordinate().getY()))
                .title(mop.getPlace())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_mop_icon))
                .snippet(String.format("Liczba wolnych miejsc dla Tir-ów: %d", mop.getOccupiedTruckPlaces()))));

        markersList.forEach(marker -> mMap.addMarker(marker));
    }
}
