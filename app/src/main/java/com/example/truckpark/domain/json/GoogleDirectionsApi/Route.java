package com.example.truckpark.domain.json.GoogleDirectionsApi;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties({"copyrights","summary","warnings","waypoint_order"})
public class Route {

    @JsonProperty("bounds")
    private Bounds bounds;

    @JsonProperty("legs")
    private List<Leg> legs;

    @JsonProperty("overview_polyline")
    private Polyline polyline;

    public Route() {
    }

    public Route(Bounds bounds, List<Leg> legs, Polyline polyline) {
        this.bounds = bounds;
        this.legs = legs;
        this.polyline = polyline;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }
}
