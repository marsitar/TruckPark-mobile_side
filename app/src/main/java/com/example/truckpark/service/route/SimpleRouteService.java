package com.example.truckpark.service.route;

import android.content.Context;

import com.example.truckpark.domain.json.GoogleDirectionsApi.GoogleRoute;
import com.example.truckpark.domain.json.GoogleDirectionsApi.LatLng;
import com.example.truckpark.domain.json.GoogleDirectionsApi.Step;

import java.util.ArrayList;
import java.util.List;

public class SimpleRouteService {

    public List<Double[]> getSimpleRoute(String origin, String destination, Context context){

        List<Double[]> points = new ArrayList<>();

        RequestGoogleRouteService requestGoogleRouteService = new RequestGoogleRouteService(context);

        GoogleRoute googleRoute = requestGoogleRouteService.getGoogleRoute(origin, destination);

        List<Step> steps= googleRoute.getRoutes().get(0).getLegs().get(0).getSteps();

        fillPointsList(points, steps);

        return points;
    }

    private void fillPointsList(List<Double[]> points, List<Step> steps) {
        steps.forEach(step -> {
                Double[] coordinates = new Double[2];
                coordinates[0] = step.getStartLocation().getLat();
                coordinates[1] = step.getStartLocation().getLng();
                points.add(coordinates);
                });
        LatLng endPoint = steps.get(steps.size()-1).getEndLocation();
        points.add(new Double[]{endPoint.getLat(),endPoint.getLng()});
    }
}
