package com.example.truckpark.repository;

public final class CurrentPosition {

    private static double currentX;
    private static double currentY;

    private CurrentPosition(){}

    public static double getCurrentX() {
        return currentX;
    }

    public static void setCurrentX(double currentX) {
        CurrentPosition.currentX = currentX;
    }

    public static double getCurrentY() {
        return currentY;
    }

    public static void setCurrentY(double currentY) {
        CurrentPosition.currentY = currentY;
    }
}
