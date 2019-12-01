package com.example.truckpark.domain.json.googledirectionsapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class GoogleRoute {

    @JsonProperty("geocoded_waypoints")
    private List<GeocodedWayPoint> geocodedWayPoints = new ArrayList<>();

    @JsonProperty("routes")
    private List<Route> routes = new ArrayList<>();

    @JsonProperty("status")
    private String status;

    public GoogleRoute() {
    }

    public GoogleRoute(List<GeocodedWayPoint> geocodedWayPoints, List<Route> routes, String status) {
        this.geocodedWayPoints = geocodedWayPoints;
        this.routes = routes;
        this.status = status;
    }

    public List<GeocodedWayPoint> getGeocodedWayPoints() {
        return geocodedWayPoints;
    }

    public void setGeocodedWayPoints(List<GeocodedWayPoint> geocodedWayPoints) {
        this.geocodedWayPoints = geocodedWayPoints;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}