package com.example.truckpark.service.optiomalizedriverstime;

import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.domain.entity.RoutePart;
import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.domain.entity.RouteSegment;
import com.example.truckpark.localdatamanagment.DataSaver;
import com.example.truckpark.localdatamanagment.RouterScheduleDataManagement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.isNotNull;

@RunWith(AndroidJUnit4.class)
public class RouteScheduleInfoWindowContentServiceTest {

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

        RouteSchedule routeSchedule = new RouteSchedule.Builder()
                .withRouteParts(Arrays.asList(routePart1, routePart2))
                .withSaveDateAndTime(LocalDateTime.now())
                .build();

        DataSaver<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        routerScheduleDataManagement.save(routeSchedule);
    }

    @Test
    public void getOriginDestinationValue_inputCorrectValues_assertIsNotNull() {
        //given
        RouteScheduleInfoWindowContentService routeScheduleInfoWindowContentService = new RouteScheduleInfoWindowContentService();
        //when
        String originDestinationValue = routeScheduleInfoWindowContentService.getOriginDestinationValue();
        //then
        assertThat(originDestinationValue, isNotNull());
    }

    @Test
    public void getOriginDestinationValue_inputCorrectValues_assertResultIsCorrect() {
        //given
        RouteScheduleInfoWindowContentService routeScheduleInfoWindowContentService = new RouteScheduleInfoWindowContentService();
        //when
        String originDestinationValue = routeScheduleInfoWindowContentService.getOriginDestinationValue();
        //then
        assertThat(originDestinationValue, equalTo("Grudziadz - Szczecin"));
    }

    @Test
    public void getFullRestDistanceValue_inputCorrectValues_assertIsNotNull() {
        //given
        RouteScheduleInfoWindowContentService routeScheduleInfoWindowContentService = new RouteScheduleInfoWindowContentService();
        //when
        String fullRestDistanceValue = routeScheduleInfoWindowContentService.getFullRestDistanceValue();
        //then
        assertThat(fullRestDistanceValue, isNotNull());
    }

    @Test
    public void getFullRestDistanceValue_inputCorrectValues_assertResultIsCorrect() {
        //given
        RouteScheduleInfoWindowContentService routeScheduleInfoWindowContentService = new RouteScheduleInfoWindowContentService();
        //when
        String fullRestDistanceValue = routeScheduleInfoWindowContentService.getFullRestDistanceValue();
        //then
        assertThat(fullRestDistanceValue, equalTo("496 km"));
    }

    @Test
    public void getFullRestTimeValue_inputCorrectValues_assertIsNotNull() {
        //given
        RouteScheduleInfoWindowContentService routeScheduleInfoWindowContentService = new RouteScheduleInfoWindowContentService();
        //when
        String fullRestTimeValue = routeScheduleInfoWindowContentService.getFullRestTimeValue();
        //then
        assertThat(fullRestTimeValue, isNotNull());
    }

    @Test
    public void getFullRestTimeValue_inputCorrectValues_assertResultIsCorrect() {
        //given
        RouteScheduleInfoWindowContentService routeScheduleInfoWindowContentService = new RouteScheduleInfoWindowContentService();
        //when
        String fullRestTimeValue = routeScheduleInfoWindowContentService.getFullRestTimeValue();
        //then
        assertThat(fullRestTimeValue, equalTo("5 h, 20 min"));
    }

    @AfterClass
    public void clearData() {
        DataSaver<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        routerScheduleDataManagement.save(null);
    }
}
