package com.example.truckpark.conventer;

import com.example.truckpark.domain.entity.RoutePart;
import com.example.truckpark.domain.entity.RouteSegment;
import com.example.truckpark.domain.json.googledirectionsapi.Data;
import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.example.truckpark.domain.json.googledirectionsapi.Leg;
import com.example.truckpark.domain.json.googledirectionsapi.Route;
import com.example.truckpark.domain.json.googledirectionsapi.Step;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GoogleRouteToRoutePartConventer {

    public RoutePart convertGoogleRouteToRouteSegment(GoogleRoute googleRoute) {

        Leg leg = getLegFromGoogleRoute(googleRoute);

        Duration duration = getDurationFromLeg(leg);
        Integer distance = getDistanceFromLeg(leg);
        String origin = getOriginFromLeg(leg);
        String destination = getDestinationFromLeg(leg);
        List<RouteSegment> routeSegments = getRouteSegmentsFromLeg(leg);

        RoutePart routePart = new RoutePart.Builder()
                .withDuration(duration)
                .withDistance(distance)
                .withOrigin(origin)
                .withDestination(destination)
                .withRouteSegments(routeSegments)
                .build();

        return routePart;
    }

    private Leg getLegFromGoogleRoute(GoogleRoute googleRoute) {

        Leg firstLeg = Optional.ofNullable(googleRoute)
                .map(GoogleRoute::getRoutes)
                .orElseGet(Collections::emptyList)
                .stream()
                .findFirst()
                .map(Route::getLegs)
                .orElseGet(Collections::emptyList)
                .stream()
                .findFirst()
                .orElse(null);

        return firstLeg;
    }

    private Duration getDurationFromLeg(Leg leg) {

        Integer duration = Optional.of(leg)
                .map(Leg::getDuration)
                .map(Data::getValue)
                .orElse(0);

        return Duration.ofSeconds(duration);
    }

    private Integer getDistanceFromLeg(Leg leg) {

        return Optional.of(leg)
                .map(Leg::getDistance)
                .map(Data::getValue)
                .orElse(0);
    }

    private String getOriginFromLeg(Leg leg){

        return Optional.of(leg)
                .map(Leg::getStartAddress)
                .orElse("");
    }

    private String getDestinationFromLeg(Leg leg){

        return Optional.of(leg)
                .map(Leg::getEndAddress)
                .orElse("");
    }

    private List<RouteSegment> getRouteSegmentsFromLeg(Leg leg) {

        List<Step> steps = getStepsFromLeg(leg);
        List<RouteSegment> routeSegments = getRouteSegmentsFromSteps(steps);

        return routeSegments;
    }

    private List<RouteSegment> getRouteSegmentsFromSteps(List<Step> steps) {

        List<RouteSegment> routeParts = steps
                .stream()
                .map(step -> {
                    StepToRouteSegmentConverter stepToRouteSegmentConverter = new StepToRouteSegmentConverter();
                    return stepToRouteSegmentConverter.convertStepToRouteSegment(step);
                })
                .collect(Collectors.toList());

        return routeParts;
    }

    private List<Step> getStepsFromLeg(Leg leg) {

        List<Step> steps = Optional.of(leg)
                .map(Leg::getSteps)
                .orElseGet(Collections::emptyList);

        return steps;
    }
}
