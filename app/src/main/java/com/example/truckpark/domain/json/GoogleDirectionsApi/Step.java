package com.example.truckpark.domain.json.GoogleDirectionsApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"maneuver"})
public class Step {

    @JsonProperty("distance")
    private Data distance;

    @JsonProperty("duration")
    private Data duration;

    @JsonProperty("end_location")
    private LatLng endLocation;

    @JsonProperty("html_instructions")
    private String htmlInstructions;

    @JsonProperty("polyline")
    private Polyline polyline;

    @JsonProperty("start_location")
    private LatLng startLocation;

    @JsonProperty("travel_mode")
    private String travelMode;

    public Step() {
    }

    public Step(Data distance, Data duration, LatLng endLocation, String htmlInstructions, Polyline polyline, LatLng startLocation, String travelMode) {
        this.distance = distance;
        this.duration = duration;
        this.endLocation = endLocation;
        this.htmlInstructions = htmlInstructions;
        this.polyline = polyline;
        this.startLocation = startLocation;
        this.travelMode = travelMode;
    }

    public Data getDistance() {
        return distance;
    }

    public void setDistance(Data distance) {
        this.distance = distance;
    }

    public Data getDuration() {
        return duration;
    }

    public void setDuration(Data duration) {
        this.duration = duration;
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }
}
