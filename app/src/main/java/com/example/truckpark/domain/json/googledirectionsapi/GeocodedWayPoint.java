package com.example.truckpark.domain.json.googledirectionsapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"types"})
public class GeocodedWayPoint {

    @JsonProperty("geocoder_status")
    private String geocoderStatus;

    @JsonProperty("place_id")
    private String placeId;

}
