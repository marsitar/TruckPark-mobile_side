package com.example.truckpark.service.optiomalizedriverstime;

import com.example.truckpark.domain.entity.RoutePart;
import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.localdatamanagment.DataGetter;
import com.example.truckpark.localdatamanagment.RouterScheduleDataManagement;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;

public class RouteScheduleInfoWindowContentService {

    public String getOriginDestinationValue(){

        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();

        String origin = getOriginFromRouteSchedule(routerScheduleDataManagement);
        String destination = getDestinationFromRouteSchedule(routerScheduleDataManagement);

        return String.format("%s - %s", origin, destination);
    }

    public String getFullRestDistanceValue(){

        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();

        int fullRestDistanceInKilometers = getFullRestDistanceFromRouteSchedule(routerScheduleDataManagement)/1000;

        return String.format("%d km", fullRestDistanceInKilometers);
    }

    public String getFullRestTimeValue() {

        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();

        Long fullRestTimeValueHours = getFullRestTimeValueHourFromRouteSchedule(routerScheduleDataManagement);
        Long fullRestTimeValueRestMinutes = getFullRestTimeValueMinutesFromRouteSchedule(routerScheduleDataManagement) - (fullRestTimeValueHours*60L);

        return String.format("%d h, %d min", fullRestTimeValueHours, fullRestTimeValueRestMinutes);

    }

    private String getDestinationFromRouteSchedule(DataGetter<RouteSchedule> routerScheduleDataManagement) {

        return Optional.of(routerScheduleDataManagement)
                .map(DataGetter::getData)
                .map(RouteSchedule::getRouteParts)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(RoutePart::getDestination)
                .reduce((first, second) -> second)
                .orElse("");
    }

    private String getOriginFromRouteSchedule(DataGetter<RouteSchedule> routerScheduleDataManagement) {

        return Optional.of(routerScheduleDataManagement)
                .map(DataGetter::getData)
                .map(RouteSchedule::getRouteParts)
                .orElseGet(Collections::emptyList)
                .stream()
                .findFirst()
                .map(RoutePart::getOrigin)
                .orElse("");
    }

    private int getFullRestDistanceFromRouteSchedule(DataGetter<RouteSchedule> routerScheduleDataManagement) {

        return Optional.of(routerScheduleDataManagement)
                .map(DataGetter::getData)
                .map(RouteSchedule::getRouteParts)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(RoutePart::getDistance)
                .mapToInt(Integer::new)
                .sum();
    }

    private long getFullRestTimeValueMinutesFromRouteSchedule(DataGetter<RouteSchedule> routerScheduleDataManagement) {
        return Optional.of(routerScheduleDataManagement)
                .map(DataGetter::getData)
                .map(RouteSchedule::getRouteParts)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(RoutePart::getDuration)
                .reduce(Duration.ZERO,(a, b) -> a.plus(b))
                .toMinutes();
    }

    private long getFullRestTimeValueHourFromRouteSchedule(DataGetter<RouteSchedule> routerScheduleDataManagement) {
        return Optional.of(routerScheduleDataManagement)
                .map(DataGetter::getData)
                .map(RouteSchedule::getRouteParts)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(RoutePart::getDuration)
                .reduce(Duration.ZERO,(a, b) -> a.plus(b))
                .toHours();
    }
}
