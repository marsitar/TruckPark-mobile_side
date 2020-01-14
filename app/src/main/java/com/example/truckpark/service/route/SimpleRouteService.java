package com.example.truckpark.service.route;

import android.content.Context;
import android.util.Log;

import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.example.truckpark.domain.json.googledirectionsapi.Leg;
import com.example.truckpark.domain.json.googledirectionsapi.Polyline;
import com.example.truckpark.domain.json.googledirectionsapi.Route;
import com.example.truckpark.domain.json.googledirectionsapi.Step;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SimpleRouteService {

    private String className = this.getClass().getSimpleName();

    public List<Double[]> getSimpleRoute(String origin, String destination, Context context) {

        List<Double[]> points = new ArrayList<>();

        GoogleRouteService googleRouteService = new GoogleRouteService(context);

        GoogleRoute googleRoute = googleRouteService.getGoogleRoute(origin, destination);

        List<Step> steps = Optional.ofNullable(googleRoute)
                .map(GoogleRoute::getRoutes)
                .orElseGet(Collections::emptyList)
                .stream()
                .findFirst()
                .map(Route::getLegs)
                .orElseGet(Collections::emptyList)
                .stream()
                .findFirst()
                .map(Leg::getSteps)
                .orElseGet(Collections::emptyList);

        fillPointsList(steps, points);

        Log.d(className, String.format("Simple route has been created with current data from GoogleMaps. Origin=%s, destination=%s", origin, destination));

        return points;
    }

    private void fillPointsList(List<Step> steps, List<Double[]> points) {

        Optional.ofNullable(steps)
            .orElseGet(Collections::emptyList)
            .forEach(step -> {
                Double[] coordinates = new Double[2];
                coordinates[0] = step.getStartLocation().getLat();
                coordinates[1] = step.getStartLocation().getLng();

                points.add(coordinates);

                List<com.google.android.gms.maps.model.LatLng> insideStepPolylinePoints = PolyUtil.decode(
                        Optional.of(step)
                                .map(Step::getPolyline)
                                .map(Polyline::getPoints)
                                .orElse("")
                );

                insideStepPolylinePoints
                        .stream()
                        .map(point -> new Double[]{point.latitude, point.longitude})
                        .forEach(points::add);
        });

        Optional.ofNullable(steps)
            .orElseGet(Collections::emptyList)
            .stream()
            .reduce((first, second) -> second)
            .map(Step::getEndLocation)
            .ifPresent(
                    latLng -> points.add(
                            new Double[]{latLng.getLat(), latLng.getLng()})
            );

        Log.d(className, String.format("Points list has been filled with current data from GoogleMaps. PointsList=%s", points.toString()));
    }
}
