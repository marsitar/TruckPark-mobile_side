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

        RouteSchedule routeSchedule = RouteSchedule.builder()
                .routeParts(Arrays.asList(routePart1, routePart2))
                .saveDateAndTime(LocalDateTime.now())
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
