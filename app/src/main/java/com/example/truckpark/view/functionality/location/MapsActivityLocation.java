package com.example.truckpark.view.functionality.location;

import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.FragmentActivity;

import com.example.truckpark.R;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.repository.CurrentMops;
import com.example.truckpark.repository.CurrentPosition;
import com.example.truckpark.service.geometry.AllGeometryGraphicsManagementService;
import com.example.truckpark.service.geometry.MopDataMarkersManagementService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class MapsActivityLocation extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap googleMap;
    private List<Mop> mops = new ArrayList<>();
    private List<MarkerOptions> markers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsActivityLocation.googleMap = googleMap;
        MapsActivityLocation.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.068716, 19.0), 5.7f));
        MapsActivityLocation.googleMap.setMyLocationEnabled(true);

        if (CurrentPosition.getCurrentPositionInstance().isLocationOn()) {
            MapsActivityLocation.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(CurrentPosition.getCurrentPositionInstance().getCurrentX(),
                            CurrentPosition.getCurrentPositionInstance().getCurrentX()),
                    15)
            );
        }

        clearAndAddMarkers();

    }

    private void clearAndAddMarkers() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (googleMap != null) {
                    AllGeometryGraphicsManagementService allGeometryGraphicsManagementService = new AllGeometryGraphicsManagementService();
                    allGeometryGraphicsManagementService.removeGraphicsFromMap(googleMap);

                    mops = CurrentMops.getCurrentMopsInstance().getCurrentMopsList();
                    markers = new ArrayList<>();

                    MopDataMarkersManagementService mopDataMarkersManagementService = new MopDataMarkersManagementService();
                    mopDataMarkersManagementService.addMarkersToMap(mops, markers, googleMap);
                }

                handler.postDelayed(this, 5000);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleMap = null;
    }
}
