package com.example.truckpark.service.optiomalizedriverstime;

import android.util.Log;

import com.example.truckpark.domain.entity.RoutePart;
import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.domain.entity.RouteSegment;
import com.example.truckpark.localdatamanagment.DataGetter;
import com.example.truckpark.localdatamanagment.RouterScheduleDataManagement;
import com.example.truckpark.view.functionality.pulloff.MapsActivityPullOff;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DisplayOnMapService {

    private final String className = this.getClass().getSimpleName();

    public List<PolylineOptions> displayRoutesIfRouteScheduleExists() {

        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();

        List<List<Double[]>> geometrySections = getGeometrySectionsFromRouterScheduleData(routerScheduleDataManagement);
        List<PolylineOptions> polylineOptions = getPolylineOptionsFromGeometrySections(geometrySections);

        Log.i(className, String.format("Polyline options: %s.", polylineOptions));

        return polylineOptions;
    }

    public List<List<Double[]>> getGeometrySectionsFromRouterScheduleData(DataGetter<RouteSchedule> routerScheduleDataManagement) {

        List<List<Double[]>> geometrySectionsFromRouterScheduleData = getRouteParts(routerScheduleDataManagement)
                .stream()
                .map(RoutePart::getRouteSegments)
                .map(routeSegments -> routeSegments.stream()
                        .flatMap(routeSegment -> {
                            List<Double[]> stepPointFullList = new ArrayList<>();
                            stepPointFullList.add(routeSegment.getPoints()[0]);
                            stepPointFullList.addAll(routeSegment.getInnerPoints());
                            stepPointFullList.add(routeSegment.getPoints()[1]);
                            return stepPointFullList.stream();
                        })
                        .distinct()
                        .collect(Collectors.toList())
                )
                .collect(Collectors.toList());

        Log.i(className, String.format("Geometry sections get from RouterScheduleData: %s.", geometrySectionsFromRouterScheduleData));

        return geometrySectionsFromRouterScheduleData;
    }

    public List<LatLng> generateStartAndEndpoints(List<PolylineOptions> polylineOptionsList) {

        List<LatLng> completedListOfPoints = new ArrayList<>();

        List<LatLng> startPoints = getStartPoints(polylineOptionsList);
        LatLng lastEndPoint = getLastEndPoint(polylineOptionsList);

        completedListOfPoints.addAll(startPoints);
        completedListOfPoints.add(lastEndPoint);

        Log.i(className, String.format("Completed list of startAndEndpoints has been generated: %s.", completedListOfPoints));

        return completedListOfPoints;
    }

    private List<LatLng> getStartPoints(List<PolylineOptions> polylineOptionsList) {

        List<LatLng> startPoints = polylineOptionsList.stream()
                .map(PolylineOptions::getPoints)
                .map(points -> points.stream().findFirst())
                .map(optLatLng -> optLatLng.orElse(null))
                .collect(Collectors.toList());

        Log.d(className, String.format("StartPoints list: %s.", startPoints));

        return startPoints;
    }

    private LatLng getLastEndPoint(List<PolylineOptions> polylineOptionsList) {

        LatLng lastEndPoint = polylineOptionsList.stream()
                .map(PolylineOptions::getPoints)
                .flatMap(Collection::stream)
                .reduce((first, second) -> second)
                .orElse(null);

        Log.d(className, String.format("Filtered potentialStopMops list: %s.", lastEndPoint));

        return lastEndPoint;
    }

    public void moveToRouteStartIfRouteScheduleExists() {

        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();

        getRouteParts(routerScheduleDataManagement)
                .stream()
                .findFirst()
                .map(RoutePart::getRouteSegments)
                .orElseGet(Collections::emptyList)
                .stream()
                .findFirst()
                .map(RouteSegment::getPoints)
                .map(arrayPoints -> arrayPoints[0])
                .ifPresent(pointPair -> {
                    MapsActivityPullOff.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pointPair[0], pointPair[1]), 15f));
                    Log.d(className, String.format("Camera has been moved to position: x=%f, y=%f.", pointPair[1],pointPair[0]));
                });

    }

    private List<PolylineOptions> getPolylineOptionsFromGeometrySections(List<List<Double[]>> geometrySections) {

        List<PolylineOptions> polylineOptionsFromGeometrySections = geometrySections.stream()
                .map(geometrySection -> {
                    PolylineOptions rectOptions = new PolylineOptions();
                    geometrySection.forEach(point -> rectOptions.add(new LatLng(point[0], point[1])));
                    return rectOptions;
                })
                .collect(Collectors.toList());

        Log.d(className, String.format("PolylineOptionsFromGeometrySections list: %s.", polylineOptionsFromGeometrySections));

        return polylineOptionsFromGeometrySections;
    }

    private List<RoutePart> getRouteParts(DataGetter<RouteSchedule> routerScheduleDataManagement) {

        List<RoutePart> routeParts = Optional.of(routerScheduleDataManagement)
                .map(DataGetter::getData)
                .map(RouteSchedule::getRouteParts)
                .orElseGet(Collections::emptyList);

        Log.d(className, String.format("RouteParts list: %s.", routeParts));

        return routeParts;
    }
}
