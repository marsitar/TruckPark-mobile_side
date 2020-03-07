package com.example.truckpark.domain.json.googledirectionsapi;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"copyrights","summary","warnings","waypoint_order"})
public class Route {

    @JsonProperty("bounds")
    private Bounds bounds;

    @JsonProperty("legs")
    private List<Leg> legs = new ArrayList<>();

    @JsonProperty("overview_polyline")
    private Polyline polyline;

}
