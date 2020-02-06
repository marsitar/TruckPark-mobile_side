package com.example.truckpark.service.geometry;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

public class AllGeometryGraphicsManagementService {

    private String className = this.getClass().getSimpleName();

    public void removeGraphicsFromMap(GoogleMap googleMap) {
        googleMap.clear();
        Log.v(className, "Markers, polygons and other shapes have been deleted from the map.");
    }
}
