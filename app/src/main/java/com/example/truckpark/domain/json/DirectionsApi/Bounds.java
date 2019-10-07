package com.example.truckpark.domain.json.DirectionsApi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bounds {

    @JsonProperty("northeast")
    private LatLng northeast;

    @JsonProperty("southwest")
    private LatLng southwest;

    public Bounds() {
    }

    public Bounds(LatLng northeast, LatLng southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    public LatLng getNortheast() {
        return northeast;
    }

    public void setNortheast(LatLng northeast) {
        this.northeast = northeast;
    }

    public LatLng getSouthwest() {
        return southwest;
    }

    public void setSouthwest(LatLng southwest) {
        this.southwest = southwest;
    }
}
