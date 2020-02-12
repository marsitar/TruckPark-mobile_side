package com.example.truckpark.service.optiomalizedriverstime;

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

    public List<PolylineOptions> displayRoutesIfRouteScheduleExists() {

        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();

        List<List<Double[]>> geometrySections = getGeometrySectionsFromRouterScheduleData(routerScheduleDataManagement);
        List<PolylineOptions> polylineOptions = getPolylineOptionsFromGeometrySections(geometrySections);

        return polylineOptions;
    }

    public List<List<Double[]>> getGeometrySectionsFromRouterScheduleData(DataGetter<RouteSchedule> routerScheduleDataManagement) {
        return getRouteParts(routerScheduleDataManagement)
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
    }

    public List<LatLng> generateStartAndEndpoints(List<PolylineOptions> polylineOptionsList) {

        List<LatLng> completedListOfPoints = new ArrayList<>();

        List<LatLng> startPoints = getStartPoints(polylineOptionsList);
        LatLng lastEndPoint = getLastEndPoint(polylineOptionsList);

        completedListOfPoints.addAll(startPoints);
        completedListOfPoints.add(lastEndPoint);

        return completedListOfPoints;
    }

    private List<LatLng> getStartPoints(List<PolylineOptions> polylineOptionsList) {
        return polylineOptionsList.stream()
                .map(PolylineOptions::getPoints)
                .map(points -> points.stream().findFirst())
                .map(optLatLng -> optLatLng.orElse(null))
                .collect(Collectors.toList());
    }

    private LatLng getLastEndPoint(List<PolylineOptions> polylineOptionsList) {
        return polylineOptionsList.stream()
                .map(PolylineOptions::getPoints)
                .flatMap(Collection::stream)
                .reduce((first, second) -> second)
                .orElse(null);
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
                .ifPresent(pointPair ->
                        MapsActivityPullOff.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pointPair[0], pointPair[1]), 15f)));
    }

    private List<PolylineOptions> getPolylineOptionsFromGeometrySections(List<List<Double[]>> geometrySections) {
        return geometrySections.stream()
                .map(geometrySection -> {
                    PolylineOptions rectOptions = new PolylineOptions();
                    geometrySection.forEach(point -> rectOptions.add(new LatLng(point[0], point[1])));
                    return rectOptions;
                })
                .collect(Collectors.toList());
    }

    private List<RoutePart> getRouteParts(DataGetter<RouteSchedule> routerScheduleDataManagement) {
        return Optional.of(routerScheduleDataManagement)
                .map(DataGetter::getData)
                .map(RouteSchedule::getRouteParts)
                .orElseGet(Collections::emptyList);
    }


}
