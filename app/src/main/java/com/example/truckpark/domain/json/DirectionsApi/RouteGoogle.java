package com.example.truckpark.domain.json.DirectionsApi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RouteGoogle {

    @JsonProperty("geocoded_waypoints")
    private List<GeocodedWayPoint> geocodedWayPoints;

    @JsonProperty("routes")
    private List<Route> routes;

    @JsonProperty("status")
    private String status;

    public RouteGoogle() {
    }

    public RouteGoogle(List<GeocodedWayPoint> geocodedWayPoints, List<Route> routes, String status) {
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
