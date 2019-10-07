package com.example.truckpark.domain.json.DirectionsApi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LatLng {

    @JsonProperty("lat")
    private double lat;

    @JsonProperty("lng")
    private double lng;

    public LatLng() {
    }

    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
