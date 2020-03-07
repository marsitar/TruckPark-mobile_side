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

        RouteSegment routeSegment1 = RouteSegment.builder()
                .distance(50000)
                .duration(Duration.ofMinutes(20))
                .innerPoints(Arrays.asList(new Double[]{53.501658, 18.680758},new Double[]{53.601691, 18.618526}))
                .points(new Double[][]{new Double[]{53.477272, 18.755241}, new Double[]{53.803176, 18.622321}})
                .build();
        RouteSegment routeSegment2 = RouteSegment.builder()
                .distance(75000)
                .duration(Duration.ofHours(1))
                .innerPoints(Arrays.asList(new Double[]{54.050348, 18.670836},new Double[]{54.237774, 18.610411}))
                .points(new Double[][]{new Double[]{53.803176, 18.622321}, new Double[]{54.398917, 18.572382}})
                .build();

        RouteSegment routeSegment3 = RouteSegment.builder()
                .distance(171000)
                .duration(Duration.ofHours(2))
                .innerPoints(Arrays.asList(new Double[]{54.606479, 18.228335},new Double[]{54.471132, 17.025148}))
                .points(new Double[][]{new Double[]{54.398917, 18.572382}, new Double[]{54.203262, 16.184649}})
                .build();
        RouteSegment routeSegment4 = RouteSegment.builder()
                .distance(200000)
                .duration(Duration.ofHours(2).ofMinutes(30))
                .innerPoints(Arrays.asList(new Double[]{54.021501, 15.842589}, new Double[]{53.780384, 15.232847}))
                .points(new Double[][]{new Double[]{54.203262, 16.184649}, new Double[]{53.433668, 14.543669}})
                .build();

        RoutePart routePart1 = RoutePart.builder()
                .routeSegments(Arrays.asList(routeSegment1,routeSegment2))
                .distance(125000)
                .duration(Duration.ofHours(1).ofMinutes(20))
                .origin("Grudziadz")
                .destination("Gdansk")
                .build();

        RoutePart routePart2 = RoutePart.builder()
                .routeSegments(Arrays.asList(routeSegment3,routeSegment4))
                .distance(371000)
                .duration(Duration.ofHours(4))
                .origin("Gdansk")
                .destination("Szczecin")
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
