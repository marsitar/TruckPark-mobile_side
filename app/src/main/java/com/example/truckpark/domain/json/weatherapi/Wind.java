package com.example.truckpark.domain.json.weatherapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Wind {

    @JsonProperty("speed")
    private Integer speed;

    @JsonProperty("deg")
    private Double deg;

    public Wind() {
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }
}
