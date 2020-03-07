package com.example.truckpark.domain.json.googledirectionsapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
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
    private List<Step> steps = new ArrayList<>();

}
