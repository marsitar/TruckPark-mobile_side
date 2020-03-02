package com.example.truckpark.service.optiomalizedriverstime;

import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.domain.entity.RoutePart;
import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.domain.entity.RouteSegment;
import com.example.truckpark.localdatamanagment.DataGetter;
import com.example.truckpark.localdatamanagment.RouterScheduleDataManagement;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class DisplayOnMapServiceTest {

    @BeforeClass
    public void prepareData(){

        RouteSegment routeSegment1 = new RouteSegment.Builder()
                .withDistance(50000)
                .withDuration(Duration.ofMinutes(20))
                .withInnerPoints(Arrays.asList(new Double[]{53.501658, 18.680758},new Double[]{53.601691, 18.618526}))
                .withPoints(new Double[][]{new Double[]{53.477272, 18.755241}, new Double[]{53.803176, 18.622321}})
                .build();
        RouteSegment routeSegment2 = new RouteSegment.Builder()
                .withDistance(75000)
                .withDuration(Duration.ofHours(1))
                .withInnerPoints(Arrays.asList(new Double[]{54.050348, 18.670836},new Double[]{54.237774, 18.610411}))
                .withPoints(new Double[][]{new Double[]{53.803176, 18.622321}, new Double[]{54.398917, 18.572382}})
                .build();

        RouteSegment routeSegment3 = new RouteSegment.Builder()
                .withDistance(171000)
                .withDuration(Duration.ofHours(2))
                .withInnerPoints(Arrays.asList(new Double[]{54.606479, 18.228335},new Double[]{54.471132, 17.025148}))
                .withPoints(new Double[][]{new Double[]{54.398917, 18.572382}, new Double[]{54.203262, 16.184649}})
                .build();
        RouteSegment routeSegment4 = new RouteSegment.Builder()
                .withDistance(200000)
                .withDuration(Duration.ofHours(2).ofMinutes(30))
                .withInnerPoints(Arrays.asList(new Double[]{54.021501, 15.842589}, new Double[]{53.780384, 15.232847}))
                .withPoints(new Double[][]{new Double[]{54.203262, 16.184649}, new Double[]{53.433668, 14.543669}})
                .build();

        RoutePart routePart1 = new RoutePart.Builder()
                .withRouteSegments(Arrays.asList(routeSegment1,routeSegment2))
                .withDistance(125000)
                .withDuration(Duration.ofHours(1).ofMinutes(20))
                .withOrigin("Grudziadz")
                .withDestination("Gdansk")
                .build();

        RoutePart routePart2 = new RoutePart.Builder()
                .withRouteSegments(Arrays.asList(routeSegment3,routeSegment4))
                .withDistance(371000)
                .withDuration(Duration.ofHours(4))
                .withOrigin("Gdansk")
                .withDestination("Szczecin")
                .build();


        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        routerScheduleDataManagement.getData().getRouteParts().addAll(Arrays.asList(routePart1,routePart2));
    }

    @Test
    public void getGeometrySectionsFromRouterScheduleData_inputCorrectValues_assertQuantityOfFeaturesIsProper() {
        //given
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        DisplayOnMapService displayOnMapService = new DisplayOnMapService();
        //when
        List<List<Double[]>> geometrySectionsFromRouterScheduleData = displayOnMapService.getGeometrySectionsFromRouterScheduleData(routerScheduleDataManagement);
        //then
        assertThat(geometrySectionsFromRouterScheduleData.size(), is(greaterThan(1)));
    }

    @Test
    public void getGeometrySectionsFromRouterScheduleData_inputCorrectValues_assertQuantityOfCoordinatesIsProper() {
        //given
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        DisplayOnMapService displayOnMapService = new DisplayOnMapService();
        //when
        List<List<Double[]>> geometrySectionsFromRouterScheduleData = displayOnMapService.getGeometrySectionsFromRouterScheduleData(routerScheduleDataManagement);
        //then
        assertThat(geometrySectionsFromRouterScheduleData.get(0).get(0).length, is(equalTo(2)));
    }

    @Test
    public void getGeometrySectionsFromRouterScheduleData_inputNullValue_assertIsNull() {
        //given
        DataGetter<RouteSchedule> routerScheduleDataManagement = null;
        DisplayOnMapService displayOnMapService = new DisplayOnMapService();
        //when
        List<List<Double[]>> geometrySectionsFromRouterScheduleData = displayOnMapService.getGeometrySectionsFromRouterScheduleData(routerScheduleDataManagement);
        //then
        assertThat(geometrySectionsFromRouterScheduleData, is(nullValue()));
    }

    @Test
    public void generateStartAndEndpoints_inputCorrectValues_assertIsNotNull() {
        //given
        List<PolylineOptions> polylineOptionsList = Arrays.asList(
                new PolylineOptions().add(new LatLng(53.477272, 18.755241), new LatLng(53.803176, 18.622321)),
                new PolylineOptions().add(new LatLng(54.203262, 16.184649), new LatLng(53.433668, 14.543669)));

        DisplayOnMapService displayOnMapService = new DisplayOnMapService();
        //when
        List<LatLng> startAndEndpoints = displayOnMapService.generateStartAndEndpoints(polylineOptionsList);
        //then
        assertThat(startAndEndpoints.size(), is(equalTo(3)));
    }

    @Test
    public void generateStartAndEndpoints_inputEmptyList_assertIsEmpty() {
        //given
        DisplayOnMapService displayOnMapService = new DisplayOnMapService();
        List<PolylineOptions> polylineOptionsList = new ArrayList<>();
        //when
        List<LatLng> startAndEndpoints = displayOnMapService.generateStartAndEndpoints(polylineOptionsList);
        //then
        assertThat(startAndEndpoints.size(), is(equalTo(0)));
    }

    @Test
    public void generateStartAndEndpoints_inputNullValue_assertIsNull() {
        //given
        DisplayOnMapService displayOnMapService = new DisplayOnMapService();
        List<PolylineOptions> polylineOptionsList = null;
        //when
        List<LatLng> startAndEndpoints = displayOnMapService.generateStartAndEndpoints(polylineOptionsList);
        //then
        assertThat(startAndEndpoints.size(), is(nullValue()));
    }

    @AfterClass
    public void clearData() {
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        routerScheduleDataManagement.getData().getRouteParts().clear();
    }
    
}
