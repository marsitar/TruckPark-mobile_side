package com.example.truckpark.domain.json.googledirectionsapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Polyline {

    @JsonProperty("points")
    private String points;

    public Polyline() {
    }

    public Polyline(String points) {
        this.points = points;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
