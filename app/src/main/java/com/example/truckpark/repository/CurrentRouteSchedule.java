package com.example.truckpark.repository;

import com.example.truckpark.domain.entity.RouteSchedule;

public final class CurrentRouteSchedule {

    private static volatile CurrentRouteSchedule CURRENT_ROUTE_SCHEDULER;

    private volatile RouteSchedule routeSchedule;

    private CurrentRouteSchedule() {
    }

    public static CurrentRouteSchedule getCurrentRouteSchedule() {
        if (CURRENT_ROUTE_SCHEDULER == null) {
            synchronized (CurrentRouteSchedule.class) {
                if (CURRENT_ROUTE_SCHEDULER == null) {
                    CURRENT_ROUTE_SCHEDULER = new CurrentRouteSchedule();
                }
            }
        }
        return CURRENT_ROUTE_SCHEDULER;
    }

    public synchronized RouteSchedule getRouteSchedule() {
        return routeSchedule;
    }

    public synchronized void setRouteSchedule(RouteSchedule routeSchedule) {
        this.routeSchedule = routeSchedule;
    }
}
