package com.example.truckpark.service.optiomalizedriverstime;

import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.localdatamanagment.DataGetter;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;

public class PolylineMessageContentService {

    public String getRoutePartOrigin(DataGetter<RouteSchedule> routerScheduleDataManagement, int polylineIndex) {
        return Optional.of(routerScheduleDataManagement)
                .map(DataGetter::getData)
                .map(RouteSchedule::getRouteParts)
                .orElseGet(Collections::emptyList)
                .get(polylineIndex)
                .getOrigin();
    }

    public String getRoutePartDestination(DataGetter<RouteSchedule> routerScheduleDataManagement, int polylineIndex) {
        return Optional.of(routerScheduleDataManagement)
                .map(DataGetter::getData)
                .map(RouteSchedule::getRouteParts)
                .orElseGet(Collections::emptyList)
                .get(polylineIndex)
                .getDestination();
    }

    public Duration getRoutePartDuration(DataGetter<RouteSchedule> routerScheduleDataManagement, int polylineIndex) {
        return Optional.of(routerScheduleDataManagement)
                .map(DataGetter::getData)
                .map(RouteSchedule::getRouteParts)
                .orElseGet(Collections::emptyList)
                .get(polylineIndex)
                .getDuration();
    }

    public int getRouteDistance(DataGetter<RouteSchedule> routerScheduleDataManagement, int polylineIndex) {
        return Optional.of(routerScheduleDataManagement)
                .map(DataGetter::getData)
                .map(RouteSchedule::getRouteParts)
                .orElseGet(Collections::emptyList)
                .get(polylineIndex)
                .getDistance();
    }
}
