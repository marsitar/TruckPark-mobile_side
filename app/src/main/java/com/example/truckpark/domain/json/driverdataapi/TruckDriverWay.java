package com.example.truckpark.domain.json.driverdataapi;

import com.example.truckpark.domain.json.positionapi.Coordinate;

import java.time.LocalDateTime;

public class TruckDriverWay {

    private Long id;

    private Double fuel;

    private Double distance;

    private LocalDateTime dateTime;

    private Coordinate coordinate;

    private Truck truck;

    private Driver driver;

    public TruckDriverWay() {
    }

    public TruckDriverWay(Long id, Double fuel, Double distance, LocalDateTime dateTime, Coordinate coordinate, Truck truck, Driver driver) {
        this.id = id;
        this.fuel = fuel;
        this.distance = distance;
        this.dateTime = dateTime;
        this.coordinate = coordinate;
        this.truck = truck;
        this.driver = driver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
