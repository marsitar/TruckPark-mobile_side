package com.example.truckpark.repository;

public final class CurrentPosition {

    private static volatile CurrentPosition CURRENT_POSITION;
    private double currentX;
    private double currentY;

    private CurrentPosition(){}

    public static CurrentPosition getCurrentPositionInstance() {
        if(CURRENT_POSITION == null) {
            synchronized (CurrentPosition.class) {
                if (CURRENT_POSITION == null) {
                    CURRENT_POSITION = new CurrentPosition();
                }
            }
        }
        return CURRENT_POSITION;
    }

    public double getCurrentX() {
        return currentX;
    }

    public void setCurrentX(double currentX) {
        this.currentX = currentX;
    }

    public double getCurrentY() {
        return currentY;
    }

    public void setCurrentY(double currentY) {
        this.currentY = currentY;
    }
}
