package com.example.truckpark.view.functionality.pulloff;

import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.FragmentActivity;

import com.example.truckpark.R;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.repository.CurrentMops;
import com.example.truckpark.service.mopdata.MopDataMarkersManagementService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivityPulloff extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap googleMap;

    private List<Mop> mops;
    private List<MarkerOptions> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_pulloff);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mops = CurrentMops.getCurrentMopsInstance().getCurrentMopsList();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsActivityPulloff.googleMap = googleMap;

        MapsActivityPulloff.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.068716, 19.0), 5.7f));

        MapsActivityPulloff.googleMap.setMyLocationEnabled(true);

        clearAndAddMarkers();
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
                }

                handler.postDelayed(this, 5000);
            }
        });
    }

}
