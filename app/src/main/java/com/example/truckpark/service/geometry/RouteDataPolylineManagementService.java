package com.example.truckpark.service.geometry;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.List;

public class RouteDataPolylineManagementService {

    private String className = this.getClass().getSimpleName();

    public void addPolylinesToMap(List<Polyline> polylines, List<PolylineOptions> polylineOptionsList, GoogleMap googleMap) {
        polylineOptionsList.forEach(rectOption -> {
            Polyline polyline = googleMap.addPolyline(rectOption
                    .color(0xFF008080)
                    .width(15)
                    .clickable(true)
                    .startCap(new RoundCap())
                    .endCap(new RoundCap()));
            polylines.add(polyline);
        });
        Log.v(className, "Polylines have been added to the map.");
    }
}
