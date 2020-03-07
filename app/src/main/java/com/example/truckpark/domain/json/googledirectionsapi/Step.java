package com.example.truckpark.domain.json.googledirectionsapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
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

}
