package com.example.truckpark.view.functionality.pulloff;

import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.FragmentActivity;

import com.example.truckpark.R;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.repository.CurrentMops;
import com.example.truckpark.service.geometry.AllGeometryGraphicsManagementService;
import com.example.truckpark.service.geometry.MopDataMarkersManagementService;
import com.example.truckpark.service.geometry.RouteDataPolylineManagementService;
import com.example.truckpark.service.geometry.RouteStartAndEndpointsCircleManagementService;
import com.example.truckpark.service.optiomalizedriverstime.DisplayOnMapService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivityPullOff extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap googleMap;

    private List<Mop> mops;
    private List<MarkerOptions> markers = new ArrayList<>();
    private List<PolylineOptions> polylineOptionsList = new ArrayList<>();
    private List<Polyline> polylines = new ArrayList<>();
    private List<LatLng> startAndEndpoints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_pulloff);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        refreshMops();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsActivityPullOff.googleMap = googleMap;
        MapsActivityPullOff.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.068716, 19.0), 5.7f));
        MapsActivityPullOff.googleMap.setMyLocationEnabled(true);

        DisplayOnMapService displayOnMapService = new DisplayOnMapService();
        polylineOptionsList = displayOnMapService.displayRoutesIfRouteScheduleExists();
        startAndEndpoints = displayOnMapService.generateStartAndEndpoints(polylineOptionsList);

        displayOnMapService.moveToRouteStartIfRouteScheduleExists();
        clearAndAddMarkers();

    }

    private void clearAndAddMarkers() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (googleMap != null) {

                    removeAllGeometries();
                    refreshMops();
                    addMopMarkers();
                    addRoutesPolylines();
                    addStartAndEndRouteCircles();
                }

                handler.postDelayed(this, 5000);
            }
        });
    }

    private void removeAllGeometries() {
        AllGeometryGraphicsManagementService allGeometryGraphicsManagementService = new AllGeometryGraphicsManagementService();
        allGeometryGraphicsManagementService.removeGraphicsFromMap(googleMap);
    }

    private void refreshMops() {
        mops = CurrentMops.getCurrentMopsInstance().getCurrentMopsList();
    }

    private void addMopMarkers() {
        markers = new ArrayList<>();
        MopDataMarkersManagementService mopDataMarkersManagementService = new MopDataMarkersManagementService();
        mopDataMarkersManagementService.addMarkersToMap(mops, markers, googleMap);
    }

    private void addRoutesPolylines() {
        polylines = new ArrayList<>();
        RouteDataPolylineManagementService routeDataPolylineManagementService = new RouteDataPolylineManagementService();
        routeDataPolylineManagementService.addPolylinesToMap(polylines, polylineOptionsList, googleMap);
    }

    private void addStartAndEndRouteCircles() {
        RouteStartAndEndpointsCircleManagementService routeStartAndEndpointsCircleManagementService = new RouteStartAndEndpointsCircleManagementService();
        routeStartAndEndpointsCircleManagementService.addStartAndEndpointsCirclesToMap(startAndEndpoints, googleMap);
    }

}
