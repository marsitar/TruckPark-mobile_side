package com.example.truckpark.view.functionality.pulloff;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import androidx.fragment.app.FragmentActivity;

import com.example.truckpark.R;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.repository.CurrentMops;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivityPulloff extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap mMap;

    private List<Mop> allMops;
    private List<MarkerOptions> markersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_pulloff);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.allMops = CurrentMops.getCurrentMopsInstance().getCurrentMopsList();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.068716, 19.0), 5.7f));

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
