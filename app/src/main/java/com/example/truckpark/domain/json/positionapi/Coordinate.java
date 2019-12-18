package com.example.truckpark.domain.json.positionapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinate {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("x")
    private Double x;

    @JsonProperty("y")
    private Double y;

    public Coordinate() {
    }

    public Coordinate(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Long id, Double x, Double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
