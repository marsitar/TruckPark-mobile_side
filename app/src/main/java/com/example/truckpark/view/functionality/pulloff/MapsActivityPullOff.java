package com.example.truckpark.view.functionality.pulloff;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.truckpark.R;
import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.localdatamanagment.DataGetter;
import com.example.truckpark.localdatamanagment.RouterScheduleDataManagement;
import com.example.truckpark.repository.CurrentMops;
import com.example.truckpark.service.geometry.AllGeometryGraphicsManagementService;
import com.example.truckpark.service.geometry.MopDataMarkersManagementService;
import com.example.truckpark.service.geometry.RouteDataPolylineManagementService;
import com.example.truckpark.service.geometry.RouteStartAndEndpointsCircleManagementService;
import com.example.truckpark.service.optiomalizedriverstime.DisplayOnMapService;
import com.example.truckpark.service.optiomalizedriverstime.PolylineMessageContentService;
import com.example.truckpark.service.optiomalizedriverstime.RouteScheduleInfoWindowContentService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MapsActivityPullOff extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener {

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
        refreshMops();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsActivityPullOff.googleMap = googleMap;
        MapsActivityPullOff.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.068716, 19.0), 5.7f));
        MapsActivityPullOff.googleMap.setMyLocationEnabled(true);
        MapsActivityPullOff.googleMap.setOnPolylineClickListener(this);

        DisplayOnMapService displayOnMapService = new DisplayOnMapService();
        polylineOptionsList = displayOnMapService.displayRoutesIfRouteScheduleExists();
        startAndEndpoints = displayOnMapService.generateStartAndEndpoints(polylineOptionsList);

        displayOnMapService.moveToRouteStartIfRouteScheduleExists();
        clearAndAddMarkers();
        refreshRouteScheduleValues();

    }

    private void clearAndAddMarkers() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (googleMap != null) {
                    removeAllGeometries();
                    refreshMops();
                    addMopMarkers();
                    addRoutesPolylines();
                    addStartAndEndRouteCircles();
                }

                handler.postDelayed(this, 5000);
            }
        });
    }

    private void removeAllGeometries() {
        AllGeometryGraphicsManagementService allGeometryGraphicsManagementService = new AllGeometryGraphicsManagementService();
        allGeometryGraphicsManagementService.removeGraphicsFromMap(googleMap);
    }

    private void refreshMops() {
        mops = CurrentMops.getCurrentMopsInstance().getCurrentMopsList();
    }

    private void addMopMarkers() {
        markers = new ArrayList<>();
        MopDataMarkersManagementService mopDataMarkersManagementService = new MopDataMarkersManagementService();
        mopDataMarkersManagementService.addMarkersToMap(mops, markers, googleMap);
    }

    private void addRoutesPolylines() {
        polylines = new ArrayList<>();
        RouteDataPolylineManagementService routeDataPolylineManagementService = new RouteDataPolylineManagementService();
        routeDataPolylineManagementService.addPolylinesToMap(polylines, polylineOptionsList, googleMap);
    }

    private void addStartAndEndRouteCircles() {
        RouteStartAndEndpointsCircleManagementService routeStartAndEndpointsCircleManagementService = new RouteStartAndEndpointsCircleManagementService();
        routeStartAndEndpointsCircleManagementService.addStartAndEndpointsCirclesToMap(startAndEndpoints, googleMap);
    }

    private void refreshRouteScheduleValues() {

        TextView originDestinationValue = findViewById(R.id.full_origin_destination);
        TextView fullRestDistanceValue = findViewById(R.id.full_rest_distance_value);
        TextView fullRestTimeValue = findViewById(R.id.full_rest_time_value);

        RouteScheduleInfoWindowContentService routeScheduleInfoWindowContentService = new RouteScheduleInfoWindowContentService();
        String originDestination = routeScheduleInfoWindowContentService.getOriginDestinationValue();
        String fullRestDistance = routeScheduleInfoWindowContentService.getFullRestDistanceValue();
        String fullRestTime = routeScheduleInfoWindowContentService.getFullRestTimeValue();

        originDestinationValue.setText(originDestination);
        fullRestDistanceValue.setText(fullRestDistance);
        fullRestTimeValue.setText(fullRestTime);
    }

    @Override
    public void onPolylineClick(Polyline polyline) {

        final int polylineIndex = polylines.indexOf(polyline);
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();

        PolylineMessageContentService polylineMessageContentService = new PolylineMessageContentService();
        String origin = polylineMessageContentService.getRoutePartOrigin(routerScheduleDataManagement, polylineIndex);
        String destination = polylineMessageContentService.getRoutePartDestination(routerScheduleDataManagement, polylineIndex);
        int distance = polylineMessageContentService.getRouteDistance(routerScheduleDataManagement, polylineIndex);
        Duration duration = polylineMessageContentService.getRoutePartDuration(routerScheduleDataManagement, polylineIndex);

        View polylineToastLayout = generatePolylineToastViewWithTextValues(origin, destination, distance, duration);
        printPolylineToast(polylineToastLayout);

    }

    private View generatePolylineToastViewWithTextValues(String origin, String destination, int distance, Duration duration) {

        LayoutInflater layoutInflater = getLayoutInflater();
        View toastLayout = layoutInflater.inflate(R.layout.polyline_toast, findViewById(R.id.polyline_toast));
        toastLayout.setClipToOutline(true);

        TextView originValue = toastLayout.findViewById(R.id.origin_value);
        TextView destinationValue = toastLayout.findViewById(R.id.destination_value);
        TextView distanceValue = toastLayout.findViewById(R.id.distance_value);
        TextView durationValue = toastLayout.findViewById(R.id.duration_value);

        int distanceInKilometers = distance / 1000;
        long durationInHours = duration.toHours();
        long restDurationInMinutes = duration.toMinutes() - duration.toHours() * 60l;

        originValue.setText(origin);
        destinationValue.setText(destination);
        distanceValue.setText(String.format("%d km", distanceInKilometers));
        durationValue.setText(String.format("%d h, %d min.", durationInHours, restDurationInMinutes));

        return toastLayout;
    }

    private void printPolylineToast(View toastLayout) {
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(toastLayout);
        toast.show();
    }

}
