package com.example.truckpark.repository;

public final class CurrentState {

    private static volatile CurrentState CURRENT_STATE;
    private volatile boolean isPositionIsBeingSent = false;

    private CurrentState(){}

    public static CurrentState getCurrentStateInstance() {
        if(CURRENT_STATE == null) {
            synchronized (CurrentState.class) {
                if (CURRENT_STATE == null) {
                    CURRENT_STATE = new CurrentState();
                }
            }
        }
        return CURRENT_STATE;
    }

    public synchronized boolean isPositionIsBeingSent() {
        return isPositionIsBeingSent;
    }

    public synchronized void setPositionIsBeingSent(boolean positionIsBeingSent) {
        isPositionIsBeingSent = positionIsBeingSent;
    }
}
