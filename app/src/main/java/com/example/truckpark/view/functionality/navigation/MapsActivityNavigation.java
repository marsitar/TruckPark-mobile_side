package com.example.truckpark.view.functionality.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.FragmentActivity;

import com.example.truckpark.R;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.repository.CurrentMops;
import com.example.truckpark.service.mopdata.MopDataMarkersManagementService;
import com.example.truckpark.service.route.SimpleRouteService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivityNavigation extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap googleMap;
    public static final String SRC = "src";
    public static final String DST = "dst";

    private List<Mop> mops;
    private List<MarkerOptions> markers = new ArrayList<>();
    private PolylineOptions rectOptions = new PolylineOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_navigation);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.mops = CurrentMops.getCurrentMopsInstance().getCurrentMopsList();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent intent = getIntent();
        String src = intent.getStringExtra(SRC);
        String dst = intent.getStringExtra(DST);
        MapsActivityNavigation.googleMap = googleMap;

        LatLngBounds latLngBounds = generateObjectToBePolylineAndLatLngBounds(src, dst);

        MapsActivityNavigation.googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 20));
        MapsActivityNavigation.googleMap.setMyLocationEnabled(true);

        clearAndAddMarkers();

    }

    private LatLngBounds generateObjectToBePolylineAndLatLngBounds(String src, String dst) {
        SimpleRouteService simpleRouteService = new SimpleRouteService();

        List<Double[]> routeCoordinates = simpleRouteService.getSimpleRoute(src, dst, getApplicationContext());

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        routeCoordinates.forEach(coordinatesPair -> {
            LatLng latLng = new LatLng(coordinatesPair[0], coordinatesPair[1]);
            builder.include(latLng);
            rectOptions.add(latLng);
        });

        return builder.build();
    }

    private void clearAndAddMarkers() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (googleMap != null) {
                    MopDataMarkersManagementService mopDataMarkersManagementService = new MopDataMarkersManagementService();
                    mopDataMarkersManagementService.removeMarkersFromMap(googleMap);

                    mops = CurrentMops.getCurrentMopsInstance().getCurrentMopsList();
                    markers = new ArrayList<>();

                    mopDataMarkersManagementService.addMarkersToMap(mops, markers, googleMap);

                    Polyline polyline = MapsActivityNavigation.googleMap.addPolyline(rectOptions);
                }

                handler.postDelayed(this, 5000);
            }
        });
    }

}
