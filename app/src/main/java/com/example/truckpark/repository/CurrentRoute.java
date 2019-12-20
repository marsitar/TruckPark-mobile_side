package com.example.truckpark.repository;

import java.util.ArrayList;
import java.util.List;

public final class CurrentRoute {

    private static volatile CurrentRoute CURRENT_ROUTE;

    private volatile List<Double[]> routeCoordinates = new ArrayList<>();

    private CurrentRoute(){}

    public static CurrentRoute getCurrentStateInstance() {
        if(CURRENT_ROUTE == null) {
            synchronized (CurrentRoute.class) {
                if (CURRENT_ROUTE == null) {
                    CURRENT_ROUTE = new CurrentRoute();
                }
            }
        }
        return CURRENT_ROUTE;
    }

    public synchronized List<Double[]> getRouteCoordinates() {
        return routeCoordinates;
    }

    public synchronized void setRouteCoordinates(List<Double[]> routeCoordinates) {
        this.routeCoordinates = routeCoordinates;
    }
}
