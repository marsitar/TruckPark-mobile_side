package com.example.truckpark.domain.json.truckdriverwayapi;

public class TruckDriverWayDtoCreate {

    private Double fuel;
    private Double distance;
    private String resultTime;
    private CoordinateDto coordinateDto;

    public TruckDriverWayDtoCreate() {
    }

    public Double getFuel() {
        return fuel;
    }

    public void setFuel(Double fuel) {
        this.fuel = fuel;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getResultTime() {
        return resultTime;
    }

    public void setResultTime(String resultTime) {
        this.resultTime = resultTime;
    }

    public CoordinateDto getCoordinateDto() {
        return coordinateDto;
    }

    public void setCoordinateDto(CoordinateDto coordinateDto) {
        this.coordinateDto = coordinateDto;
    }

    @Override
    public String toString() {
        return "TruckDriverWayDtoCreate{" +
                "fuel=" + fuel +
                ", distance=" + distance +
                ", resultTime=" + resultTime +
                ", coordinateDto=" + coordinateDto +
                '}';
    }
}
