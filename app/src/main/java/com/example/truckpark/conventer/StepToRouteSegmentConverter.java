package com.example.truckpark.conventer;

import com.example.truckpark.domain.entity.RouteSegment;
import com.example.truckpark.domain.json.googledirectionsapi.Data;
import com.example.truckpark.domain.json.googledirectionsapi.LatLng;
import com.example.truckpark.domain.json.googledirectionsapi.Step;

import java.time.Duration;
import java.util.Optional;

public class StepToRouteSegmentConverter {

    public RouteSegment convertStepToRouteSegment(Step stepToConvert) {

        Step step = Optional.ofNullable(stepToConvert)
                .orElse(null);

        Duration duration = getDurationFromStep(step);
        Integer distance = getDistanceFromStep(step);
        Double[][] points = getPointsFromStep(step);

        RouteSegment routeSegment = new RouteSegment.Builder()
                .withDuration(duration)
                .withDistance(distance)
                .withPoints(points)
                .build();


        return routeSegment;
    }

    private Duration getDurationFromStep(Step step) {

        Integer duration = Optional.ofNullable(step)
                .map(Step::getDuration)
                .map(Data::getValue)
                .orElse(0);

        return Duration.ofSeconds(duration);
    }

    private Integer getDistanceFromStep(Step step) {

        return Optional.ofNullable(step)
                .map(Step::getDistance)
                .map(Data::getValue)
                .orElse(0);
    }

    private Double[][] getPointsFromStep(Step step) {

        Optional<Step> optionalStep = Optional.ofNullable(step);

        Double startLocationX = optionalStep
                .map(Step::getStartLocation)
                .map(LatLng::getLng)
                .orElse(null);
        Double startLocationY = optionalStep
                .map(Step::getStartLocation)
                .map(LatLng::getLat)
                .orElse(null);

        Double endLocationX = optionalStep
                .map(Step::getEndLocation)
                .map(LatLng::getLng)
                .orElse(null);

        Double endLocationY = optionalStep
                .map(Step::getEndLocation)
                .map(LatLng::getLat)
                .orElse(null);

        Double[] startLocation = new Double[]{startLocationX, startLocationY};
        Double[] endLocation = new Double[]{endLocationX, endLocationY};

        return new Double[][]{startLocation, endLocation};
    }
}
