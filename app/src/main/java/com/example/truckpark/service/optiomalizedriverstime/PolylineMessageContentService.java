package com.example.truckpark.service.optiomalizedriverstime;

import android.util.Log;

import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.localdatamanagment.DataGetter;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;

public class PolylineMessageContentService {

    private final String className = this.getClass().getSimpleName();

    public String getRoutePartOrigin(DataGetter<RouteSchedule> routerScheduleDataManagement, int polylineIndex) {

        String routePartOrigin = Optional.of(routerScheduleDataManagement)
                .map(DataGetter::getData)
                .map(RouteSchedule::getRouteParts)
                .orElseGet(Collections::emptyList)
                .get(polylineIndex)
                .getOrigin();

        Log.d(className, String.format("RoutePartOrigin: %s.", routePartOrigin));

        return routePartOrigin;
    }

    public String getRoutePartDestination(DataGetter<RouteSchedule> routerScheduleDataManagement, int polylineIndex) {

        String routePartDestination = Optional.of(routerScheduleDataManagement)
                .map(DataGetter::getData)
                .map(RouteSchedule::getRouteParts)
                .orElseGet(Collections::emptyList)
                .get(polylineIndex)
                .getDestination();

        Log.d(className, String.format("RoutePartDestination: %s.", routePartDestination));

        return routePartDestination;
    }

    public Duration getRoutePartDuration(DataGetter<RouteSchedule> routerScheduleDataManagement, int polylineIndex) {

        Duration routePartDuration = Optional.of(routerScheduleDataManagement)
                .map(DataGetter::getData)
                .map(RouteSchedule::getRouteParts)
                .orElseGet(Collections::emptyList)
                .get(polylineIndex)
                .getDuration();

        Log.d(className, String.format("RoutePartDuration: %s.", routePartDuration));

        return routePartDuration;
    }

    public int getRouteDistance(DataGetter<RouteSchedule> routerScheduleDataManagement, int polylineIndex) {

        int routeDistance = Optional.of(routerScheduleDataManagement)
                .map(DataGetter::getData)
                .map(RouteSchedule::getRouteParts)
                .orElseGet(Collections::emptyList)
                .get(polylineIndex)
                .getDistance();

        Log.d(className, String.format("RouteDistance: %s.", routeDistance));

        return routeDistance;
    }
}
