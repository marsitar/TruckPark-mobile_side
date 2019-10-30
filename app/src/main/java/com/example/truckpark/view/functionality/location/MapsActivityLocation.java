package com.example.truckpark.view.functionality.location;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.truckpark.R;
import com.example.truckpark.service.location.LocationDeviceService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivityLocation extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (LocationDeviceService.lastLocation != null && LocationDeviceService.mCurrLocationMarker==null){
            LocationDeviceService.mCurrLocationMarker = MapsActivityLocation.mMap.addMarker(new MarkerOptions().position(new LatLng(LocationDeviceService.lastLocation.getLatitude(), LocationDeviceService.lastLocation.getLongitude())).title("Truck Position"));
            MapsActivityLocation.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LocationDeviceService.lastLocation.getLatitude(), LocationDeviceService.lastLocation.getLongitude()),15));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mMap=null;
        LocationDeviceService.mCurrLocationMarker=null;
    }
}
