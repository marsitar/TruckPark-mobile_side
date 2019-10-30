package com.example.truckpark.repository;

import java.util.ArrayList;
import java.util.List;

public class CurrentRoute {

    public static List<Double[]> routeCoordinates = new ArrayList<>();

    public static List<Double[]> getRouteCoordinates() {
        return routeCoordinates;
    }

    public static void setRouteCoordinates(List<Double[]> routeCoordinates) {
        CurrentRoute.routeCoordinates = routeCoordinates;
    }
}
