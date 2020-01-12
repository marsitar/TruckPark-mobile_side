package com.example.truckpark.service.route;

import android.content.Context;
import android.util.Log;

import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.example.truckpark.domain.json.googledirectionsapi.LatLng;
import com.example.truckpark.domain.json.googledirectionsapi.Step;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

public class SimpleRouteService {

    private String className = this.getClass().getSimpleName();

    public List<Double[]> getSimpleRoute(String origin, String destination, Context context) {

        List<Double[]> points = new ArrayList<>();

        GoogleRouteService googleRouteService = new GoogleRouteService(context);

        GoogleRoute googleRoute = googleRouteService.getGoogleRoute(origin, destination);

        List<Step> steps = googleRoute.getRoutes().get(0).getLegs().get(0).getSteps();

        fillPointsList(points, steps);

        Log.d(className, String.format("Simple route has been created with current data from GoogleMaps. Origin=%s, destination=%s", origin, destination));

        return points;
    }

    private void fillPointsList(List<Double[]> points, List<Step> steps) {

        steps.forEach(step -> {
            Double[] coordinates = new Double[2];
            coordinates[0] = step.getStartLocation().getLat();
            coordinates[1] = step.getStartLocation().getLng();

            points.add(coordinates);

            List<com.google.android.gms.maps.model.LatLng> insideStepPolylinePoints = PolyUtil.decode(step.getPolyline().getPoints());

            insideStepPolylinePoints.forEach(point -> {
                Double[] coordinatesInsidePolygon = new Double[2];
                coordinatesInsidePolygon[0] = point.latitude;
                coordinatesInsidePolygon[1] = point.longitude;
                points.add(coordinatesInsidePolygon);
            });

        });

        LatLng endPoint = steps.get(steps.size() - 1).getEndLocation();
        points.add(new Double[]{endPoint.getLat(), endPoint.getLng()});

        Log.d(className, String.format("Points list has been filled with current data from GoogleMaps. PointsList=%s", points.toArray()));
    }
}
