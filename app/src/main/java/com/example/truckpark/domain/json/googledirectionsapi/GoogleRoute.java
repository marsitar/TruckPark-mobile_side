package com.example.truckpark.domain.json.googledirectionsapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleRoute {

    @JsonProperty("geocoded_waypoints")
    private List<GeocodedWayPoint> geocodedWayPoints = new ArrayList<>();

    @JsonProperty("routes")
    private List<Route> routes = new ArrayList<>();

    @JsonProperty("status")
    private String status;

}
