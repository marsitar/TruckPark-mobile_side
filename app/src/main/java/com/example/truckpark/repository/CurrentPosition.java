package com.example.truckpark.repository;

public final class CurrentPosition {

    private static volatile CurrentPosition CURRENT_POSITION;
    private volatile double currentX;
    private volatile double currentY;
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

    public synchronized double getCurrentX() {
        return currentX;
    }

    public synchronized void setCurrentX(double currentX) {
        this.currentX = currentX;
    }

    public synchronized double getCurrentY() {
        return currentY;
    }

    public synchronized void setCurrentY(double currentY) {
        this.currentY = currentY;
    }

    public synchronized boolean isLocationOn() {
        return isLocationOn;
    }

    public synchronized void setLocationOn(boolean locationOn) {
        isLocationOn = locationOn;
    }
}
