package com.example.truckpark.repository;

public final class CurrentPosition {

    private static volatile CurrentPosition CURRENT_POSITION;
    private volatile double currentLat;
    private volatile double currentLng;
    private volatile boolean isLocationOn = false;

    private CurrentPosition() {
    }

    public static CurrentPosition getCurrentPositionInstance() {
        if (CURRENT_POSITION == null) {
            synchronized (CurrentPosition.class) {
                if (CURRENT_POSITION == null) {
                    CURRENT_POSITION = new CurrentPosition();
                }
            }
        }
        return CURRENT_POSITION;
    }

    public synchronized double getCurrentLat() {
        return currentLat;
    }

    public synchronized void setCurrentLat(double currentLat) {
        this.currentLat = currentLat;
    }

    public synchronized double getCurrentLng() {
        return currentLng;
    }

    public synchronized void setCurrentLng(double currentLng) {
        this.currentLng = currentLng;
    }

    public synchronized boolean isLocationOn() {
        return isLocationOn;
    }

    public synchronized void setLocationOn(boolean locationOn) {
        isLocationOn = locationOn;
    }
}
