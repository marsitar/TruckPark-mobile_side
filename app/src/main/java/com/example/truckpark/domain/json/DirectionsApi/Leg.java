package com.example.truckpark.domain.json.DirectionsApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"traffic_speed_entry","via_waypoint"})
public class Leg {

    @JsonProperty("distance")
    private Data distance;

    @JsonProperty("duration")
    private Data duration;

    @JsonProperty("end_address")
    private String endAddress;

    @JsonProperty("end_location")
    private LatLng endLocation;

    @JsonProperty("start_address")
    private String startAddress;

    @JsonProperty("start_location")
    private LatLng startLocation;

    @JsonProperty("steps")
    private Step steps;

    public Leg() {
    }

    public Leg(Data distance, Data duration, String endAddress, LatLng endLocation, String startAddress, LatLng startLocation, Step steps) {
        this.distance = distance;
        this.duration = duration;
        this.endAddress = endAddress;
        this.endLocation = endLocation;
        this.startAddress = startAddress;
        this.startLocation = startLocation;
        this.steps = steps;
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

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public Step getSteps() {
        return steps;
    }

    public void setSteps(Step steps) {
        this.steps = steps;
    }
}
