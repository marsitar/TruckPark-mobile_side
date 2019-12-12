package com.example.truckpark.view.functionality.location;

import android.os.Bundle;
import android.os.SystemClock;

import androidx.fragment.app.FragmentActivity;

import com.example.truckpark.R;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.service.location.LocationDeviceService;
import com.example.truckpark.service.mopdata.RequestMopDataService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


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
        mMap.setMyLocationEnabled(true);
        if (LocationDeviceService.lastLocation != null) {
            MapsActivityLocation.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LocationDeviceService.lastLocation.getLatitude(), LocationDeviceService.lastLocation.getLongitude()), 15));
        }

        RequestMopDataService requestMopDataService = new RequestMopDataService(this);
        List<Mop> allMops = requestMopDataService.getAllMopsData();

        allMops.forEach(mop -> mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(mop.getCoordinate().getX(), mop.getCoordinate().getY()))
                                .title(mop.getPlace())
                                .snippet(String.format("Liczba miejsc parkingowych: %d",mop.getTruckPlaces()))));



    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mMap = null;
    }
}
