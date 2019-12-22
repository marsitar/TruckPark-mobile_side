package com.example.truckpark.service.mopdata;

import com.example.truckpark.R;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MopDataMarkersManagementService {

    public void addMarkersToMap(List<Mop> allMops, List<MarkerOptions> markers, GoogleMap googleMap) {
        allMops.forEach(mop -> markers.add(new MarkerOptions()
                .position(new LatLng(mop.getCoordinate().getX(), mop.getCoordinate().getY()))
                .title(mop.getPlace())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_mop_icon))
                .snippet(String.format("Liczba wolnych miejsc dla Tir-ów: %d", mop.getOccupiedTruckPlaces()))));

        markers.forEach(googleMap::addMarker);
    }

    public void removeMarkersFromMap(GoogleMap googleMap) {
        googleMap.clear();
    }
}
