package com.example.truckpark.domain.entity;

import java.time.Duration;

public class MopForm {

    private Duration leftTime;
    private int leftKilometers;
    private String mopName;
    private int freePlacesForTrucks;

    public Duration getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(Duration leftTime) {
        this.leftTime = leftTime;
    }

    public int getLeftKilometers() {
        return leftKilometers;
    }

    public void setLeftKilometers(int leftKilometers) {
        this.leftKilometers = leftKilometers;
    }

    public String getMopName() {
        return mopName;
    }

    public void setMopName(String mopName) {
        this.mopName = mopName;
    }

    public int getFreePlacesForTrucks() {
        return freePlacesForTrucks;
    }

    public void setFreePlacesForTrucks(int freePlacesForTrucks) {
        this.freePlacesForTrucks = freePlacesForTrucks;
    }

    @Override
    public String toString() {
        return "MopForm{" +
                "leftTime=" + leftTime +
                ", leftKilometers=" + leftKilometers +
                ", mopName='" + mopName + '\'' +
                ", freePlacesForTrucks=" + freePlacesForTrucks +
                '}';
    }
}
