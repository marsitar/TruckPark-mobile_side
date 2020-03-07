package com.example.truckpark.domain.json.googledirectionsapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bounds {

    @JsonProperty("northeast")
    private LatLng northeast;

    @JsonProperty("southwest")
    private LatLng southwest;

}
