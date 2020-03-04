package com.example.truckpark.conventer;

import com.example.truckpark.domain.entity.RouteSegment;
import com.example.truckpark.domain.json.googledirectionsapi.Data;
import com.example.truckpark.domain.json.googledirectionsapi.LatLng;
import com.example.truckpark.domain.json.googledirectionsapi.Polyline;
import com.example.truckpark.domain.json.googledirectionsapi.Step;
import com.google.maps.android.PolyUtil;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StepToRouteSegmentConverter {

    public RouteSegment convertStepToRouteSegment(Step stepToConvert) {

        Step step = Optional.ofNullable(stepToConvert)
                .orElse(null);

        Duration duration = getDurationFromStep(step);
        Integer distance = getDistanceFromStep(step);
        Double[][] points = getPointsFromStep(step);
        List<Double[]> innerPoints = getInnerPolylinePointsFormStep(step);

        RouteSegment routeSegment = new RouteSegment.Builder()
                .withDuration(duration)
                .withDistance(distance)
                .withPoints(points)
                .withInnerPoints(innerPoints)
                .build();


        return routeSegment;
    }

    private Duration getDurationFromStep(Step step) {

        Integer duration = Optional.ofNullable(step)
                .map(Step::getDuration)
                .map(Data::getValue)
                .orElse(0);

        return Duration.ofMinutes(duration);
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

        Double[] startLocation = new Double[]{startLocationY, startLocationX};
        Double[] endLocation = new Double[]{endLocationY, endLocationX};

        return new Double[][]{startLocation, endLocation};
    }

    private List<Double[]> getInnerPolylinePointsFormStep(Step step) {

        List<com.google.android.gms.maps.model.LatLng> insideStepPolylinePoints = PolyUtil.decode(
                Optional.of(step)
                        .map(Step::getPolyline)
                        .map(Polyline::getPoints)
                        .orElse("")
        );

        return insideStepPolylinePoints.stream()
                .map(point -> new Double[]{point.latitude, point.longitude})
                .collect(Collectors.toList());
    }
}
