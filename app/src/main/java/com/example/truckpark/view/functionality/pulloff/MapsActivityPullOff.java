package com.example.truckpark.view.functionality.pulloff;

import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.FragmentActivity;

import com.example.truckpark.R;
import com.example.truckpark.domain.entity.RoutePart;
import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.domain.entity.RouteSegment;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.localdatamanagment.DataGetter;
import com.example.truckpark.localdatamanagment.RouterScheduleDataManagement;
import com.example.truckpark.repository.CurrentMops;
import com.example.truckpark.service.geometry.AllGeometryGraphicsManagementService;
import com.example.truckpark.service.geometry.MopDataMarkersManagementService;
import com.example.truckpark.service.geometry.RouteDataPolylineManagementService;
import com.example.truckpark.service.geometry.RouteStartAndEndpointsCircleManagementService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MapsActivityPullOff extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap googleMap;

    private List<Mop> mops;
    private List<MarkerOptions> markers = new ArrayList<>();
    private List<PolylineOptions> polylineOptionsList = new ArrayList<>();
    private List<Polyline> polylines = new ArrayList<>();
    private List<LatLng> startAndEndpoints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_pulloff);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mops = CurrentMops.getCurrentMopsInstance().getCurrentMopsList();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsActivityPullOff.googleMap = googleMap;
        MapsActivityPullOff.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.068716, 19.0), 5.7f));
        MapsActivityPullOff.googleMap.setMyLocationEnabled(true);

        polylineOptionsList = displayRoutesIfRouteScheduleExists();
        startAndEndpoints = generateStartAndEndpoints();

        clearAndAddMarkers();
        moveToRouteStartIfRouteScheduleExists();

    }

    private void clearAndAddMarkers() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (googleMap != null) {

                    AllGeometryGraphicsManagementService allGeometryGraphicsManagementService = new AllGeometryGraphicsManagementService();
                    allGeometryGraphicsManagementService.removeGraphicsFromMap(googleMap);

                    mops = CurrentMops.getCurrentMopsInstance().getCurrentMopsList();

                    markers = new ArrayList<>();
                    MopDataMarkersManagementService mopDataMarkersManagementService = new MopDataMarkersManagementService();
                    mopDataMarkersManagementService.addMarkersToMap(mops, markers, googleMap);

                    polylines = new ArrayList<>();
                    RouteDataPolylineManagementService routeDataPolylineManagementService = new RouteDataPolylineManagementService();
                    routeDataPolylineManagementService.addPolylinesToMap(polylines, polylineOptionsList, googleMap);

                    RouteStartAndEndpointsCircleManagementService routeStartAndEndpointsCircleManagementService = new RouteStartAndEndpointsCircleManagementService();
                    routeStartAndEndpointsCircleManagementService.addStartAndEndpointsCirclesToMap(startAndEndpoints, googleMap);

                }

                handler.postDelayed(this, 5000);
            }
        });
    }

    private void moveToRouteStartIfRouteScheduleExists() {

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

    private List<PolylineOptions> displayRoutesIfRouteScheduleExists() {

        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();

        List<List<Double[]>> geometrySections = getRouteParts(routerScheduleDataManagement)
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


        return getPolylineOptionsFromGeometrySections(geometrySections);
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

    private List<LatLng> generateStartAndEndpoints() {

        List<LatLng> completedListOfPoints = new ArrayList<>();

        List<LatLng> startPoints = polylineOptionsList.stream()
                .map(PolylineOptions::getPoints)
                .map(points -> points.stream().findFirst())
                .map(optLatLng -> optLatLng.orElse(null))
                .collect(Collectors.toList());

        LatLng lastEndPoint = polylineOptionsList.stream()
                .map(PolylineOptions::getPoints)
                .flatMap(Collection::stream)
                .reduce((first, second) -> second)
                .orElse(null);

        completedListOfPoints.addAll(startPoints);
        completedListOfPoints.add(lastEndPoint);

        return completedListOfPoints;
    }
}
