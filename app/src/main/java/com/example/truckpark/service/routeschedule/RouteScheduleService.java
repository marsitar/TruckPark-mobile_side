package com.example.truckpark.service.routeschedule;

import android.content.Context;
import android.util.Log;

import com.example.truckpark.conventer.GoogleRouteToRoutePartConventer;
import com.example.truckpark.domain.entity.RoutePart;
import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.example.truckpark.localdatamanagment.RouterScheduleDataManagement;
import com.example.truckpark.service.route.GoogleRouteService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RouteScheduleService {

    private final String className = this.getClass().getSimpleName();
    private Context context;

    public RouteScheduleService(Context context) {
        this.context = context;
    }

    public void saveRouteScheduler(List<String> itineraryPoints) {

        RouteSchedule routeSchedule = prepareRouteSchedule(itineraryPoints);
        RouterScheduleDataManagement routerScheduleDataManagement = new RouterScheduleDataManagement();

        routerScheduleDataManagement.save(routeSchedule);
    }

    private RouteSchedule prepareRouteSchedule(List<String> itineraryPoints) {

        GoogleRouteService googleRouteService = new GoogleRouteService(this.context);

        List<String[]> itineraryPointPairs = getItineraryPointPairsFromItineraryPoints(itineraryPoints);
        List<GoogleRoute> generatedGoogleRouts = googleRouteService.generateGoogleRouteListFromItineraryPointPairs(itineraryPointPairs);
        List<RoutePart> routeParts = getRoutePartsFromGoogleRoutes(generatedGoogleRouts);

        RouteSchedule routeSchedule = new RouteSchedule.Builder()
                .withSaveDateAndTime(LocalDateTime.now())
                .withRouteParts(routeParts)
                .build();

        Log.d(className, String.format("Prepared RouteSchedule: %s.", routeSchedule));

        return routeSchedule;
    }

    private List<String[]> getItineraryPointPairsFromItineraryPoints(List<String> itineraryPoints) {

        List<String[]> itineraryPointPairs = IntStream.range(0, itineraryPoints.size() - 1)
                .mapToObj(i -> new String[]{itineraryPoints.get(i), itineraryPoints.get(i + 1)})
                .collect(Collectors.toList());

        Log.d(className, String.format("ItineraryPointPairs has been generated from ItineraryPoints. Generated ItineraryPointPairs: %s.", itineraryPointPairs));

        return itineraryPointPairs;
    }

    private List<RoutePart> getRoutePartsFromGoogleRoutes(List<GoogleRoute> generatedGoogleRouts) {

        List<RoutePart> routeParts = generatedGoogleRouts
                .stream()
                .map(googleRoute -> {
                    GoogleRouteToRoutePartConventer googleRouteToRoutePartConventer = new GoogleRouteToRoutePartConventer();
                    return googleRouteToRoutePartConventer.convertGoogleRouteToRouteSegment(googleRoute);
                })
                .collect(Collectors.toList());

        Log.d(className, String.format("RouteParts has been generated from GoogleRoutes. Generated RouteParts: %s.", routeParts));

        return routeParts;
    }


}
