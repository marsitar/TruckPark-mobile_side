package com.example.truckpark.repository;

public final class CurrentState {

    private static boolean isPositionIsBeingSent = false;

    private CurrentState(){}

    public static boolean isIsPositionIsBeingSent() {
        return isPositionIsBeingSent;
    }

    public static void setIsPositionIsBeingSent(boolean isPositionIsBeingSent) {
        CurrentState.isPositionIsBeingSent = isPositionIsBeingSent;
    }
}
