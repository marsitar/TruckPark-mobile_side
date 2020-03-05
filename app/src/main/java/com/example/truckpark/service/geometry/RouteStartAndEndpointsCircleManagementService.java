package com.example.truckpark.service.geometry;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RouteStartAndEndpointsCircleManagementService {

    private String className = this.getClass().getSimpleName();

    public void addStartAndEndpointsCirclesToMap(List<LatLng> startAndEndpoints, GoogleMap googleMap) {
        Optional.ofNullable(startAndEndpoints)
                .orElseGet(Collections::emptyList)
                .forEach(point -> googleMap.addCircle(new CircleOptions()
                    .center(new LatLng(point.latitude, point.longitude))
                    .radius(20)
                    .strokeColor(Color.RED)
                    .visible(true)
                    .fillColor(Color.BLUE))
                );
        Log.v(className, "Start and end point circles have been added to the map.");
    }
}
