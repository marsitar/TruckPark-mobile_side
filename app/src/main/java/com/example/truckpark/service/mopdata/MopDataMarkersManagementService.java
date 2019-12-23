package com.example.truckpark.service.mopdata;

import android.util.Log;

import com.example.truckpark.R;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MopDataMarkersManagementService {

    private String className = this.getClass().getSimpleName();

    public void addMarkersToMap(List<Mop> allMops, List<MarkerOptions> markers, GoogleMap googleMap) {
        allMops.forEach(mop -> markers.add(new MarkerOptions()
                .position(new LatLng(mop.getCoordinate().getX(), mop.getCoordinate().getY()))
                .title(mop.getPlace())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_mop_icon))
                .snippet(String.format("Liczba wolnych miejsc dla Tir-ów: %d", mop.getOccupiedTruckPlaces()))));

        markers.forEach(googleMap::addMarker);
        Log.v(className, "Markers has been added to the map.");
    }

    public void removeMarkersFromMap(GoogleMap googleMap) {
        googleMap.clear();
        Log.v(className, "Markers, polygons and other shapes has been deleted from the map.");
    }
}
