package com.example.truckpark.domain.json.googledirectionsapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"types"})
public class GeocodedWayPoint {

    @JsonProperty("geocoder_status")
    private String geocoderStatus;

    @JsonProperty("place_id")
    private String placeId;

    public GeocodedWayPoint() {
    }

    public GeocodedWayPoint(String geocoderStatus, String placeId) {
        this.geocoderStatus = geocoderStatus;
        this.placeId = placeId;
    }

    public String getGeocoderStatus() {
        return geocoderStatus;
    }

    public void setGeocoderStatus(String geocoderStatus) {
        this.geocoderStatus = geocoderStatus;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
