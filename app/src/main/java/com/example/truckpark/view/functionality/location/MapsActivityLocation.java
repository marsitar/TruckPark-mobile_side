package com.example.truckpark.view.functionality.location;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.truckpark.R;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.repository.CurrentPosition;
import com.example.truckpark.service.mopdata.RequestMopDataService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MapsActivityLocation extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap mMap;
    private List<Mop> allMops;
    private RequestMopDataService requestMopDataService;
    private List<MarkerOptions> markersList = new ArrayList<>();
    private int counter=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //THINK ABOUT IT LATER,async attitute would be better here in future
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ///////////////////////////////////////////////////////////////////////////////////////////

        requestMopDataService = new RequestMopDataService(this);

        this.allMops = Optional.ofNullable(requestMopDataService)
                .map(RequestMopDataService::getAllMopsData)
                .orElse(null);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.068716, 19.0), 5.7f));
        mMap.setMyLocationEnabled(true);

        if(CurrentPosition.getCurrentPositionInstance().isLocationOn()) {
            MapsActivityLocation.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(CurrentPosition.getCurrentPositionInstance().getCurrentX(),
                            CurrentPosition.getCurrentPositionInstance().getCurrentX()),
                    15)
            );
        }

        addMarkersToMap();
        clearAndAddMarkers();

    }

    private void clearAndAddMarkers() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mMap != null){
                    mMap.clear();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
                .snippet(String.format("Liczba wolnych miejsc dla Tir-ów: %d", Optional.ofNullable(mop)
                                                                                    .map(Mop::getOccupiedTruckPlaces)
                                                                                    .orElse(null)
                ))));

        markersList.forEach(marker -> mMap.addMarker(marker));

    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mMap = null;
    }
}
