package com.example.truckpark.service.optiomalizedriverstime;

import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.domain.entity.RoutePart;
import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.domain.entity.RouteSegment;
import com.example.truckpark.localdatamanagment.DataGetter;
import com.example.truckpark.localdatamanagment.DataSaver;
import com.example.truckpark.localdatamanagment.RouterScheduleDataManagement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Duration;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class PolylineMessageContentServiceTest {

    @Before
    public static void prepareDataForTests() {
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
    public void getRoutePartOrigin_inputCorrectValuePolylineIndexZero_assertResultOriginIsProper() {
        //given
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        PolylineMessageContentService polylineMessageContentService = new PolylineMessageContentService();
        //when
        String routePartOrigin = polylineMessageContentService.getRoutePartOrigin(routerScheduleDataManagement,0);
        //then
        assertThat(routePartOrigin, is(equalTo("Grudziadz")));
    }

    @Test
    public void getRoutePartOrigin_inputCorrectValuePolylineIndexOne_assertResultOriginIsProper() {
        //given
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        PolylineMessageContentService polylineMessageContentService = new PolylineMessageContentService();
        //when
        String routePartOrigin = polylineMessageContentService.getRoutePartOrigin(routerScheduleDataManagement,1);
        //then
        assertThat(routePartOrigin, is(equalTo("Gdansk")));
    }

    @Test
    public void getRoutePartOrigin_inputInCorrectValuePolylineIndexOutOfRange_assertResultOriginIsNull() {
        //given
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        PolylineMessageContentService polylineMessageContentService = new PolylineMessageContentService();
        //when
        String routePartOrigin = polylineMessageContentService.getRoutePartOrigin(routerScheduleDataManagement,50);
        //then
        assertThat(routePartOrigin, is(nullValue()));
    }


    @Test
    public void getRoutePartDestination_inputCorrectValuePolylineIndexZero_assertResultDestinationIsProper() {
        //given
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        PolylineMessageContentService polylineMessageContentService = new PolylineMessageContentService();
        //when
        String routePartDestination = polylineMessageContentService.getRoutePartDestination(routerScheduleDataManagement, 0);
        //then
        assertThat(routePartDestination, is(equalTo("Gdansk")));
    }

    @Test
    public void getRoutePartDestination_inputCorrectValuePolylineIndexOne_assertResultDestinationIsProper() {
        //given
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        PolylineMessageContentService polylineMessageContentService = new PolylineMessageContentService();
        //when
        String routePartDestination = polylineMessageContentService.getRoutePartDestination(routerScheduleDataManagement, 1);
        //then
        assertThat(routePartDestination, is(equalTo("Szczecin")));
    }

    @Test
    public void getRoutePartDestination_inputInCorrectValuePolylineIndexOutOfRange_assertResultDestinationIsNull() {
        //given
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        PolylineMessageContentService polylineMessageContentService = new PolylineMessageContentService();
        //when
        String routePartDestination = polylineMessageContentService.getRoutePartDestination(routerScheduleDataManagement, 49);
        //then
        assertThat(routePartDestination, is(nullValue()));
    }

    @Test
    public void getRoutePartDuration_inputCorrectValuePolylineIndexZero_assertResultDurationIsProper() {
        //given
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        PolylineMessageContentService polylineMessageContentService = new PolylineMessageContentService();
        //when
        Duration routePartDuration = polylineMessageContentService.getRoutePartDuration(routerScheduleDataManagement, 0);
        //then
        assertThat(routePartDuration, is(equalTo(Duration.ofHours(1).ofMinutes(20))));
    }

    @Test
    public void getRoutePartDuration_inputCorrectValuePolylineIndexOne_assertResultDurationIsProper() {
        //given
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        PolylineMessageContentService polylineMessageContentService = new PolylineMessageContentService();
        //when
        Duration routePartDuration = polylineMessageContentService.getRoutePartDuration(routerScheduleDataManagement, 1);
        //then
        assertThat(routePartDuration, is(equalTo(Duration.ofHours(4))));
    }

    @Test
    public void getRoutePartDuration_inputInCorrectValuePolylineIndexOutOfRange_assertResultDurationIsNull() {
        //given
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        PolylineMessageContentService polylineMessageContentService = new PolylineMessageContentService();
        //when
        Duration routePartDuration = polylineMessageContentService.getRoutePartDuration(routerScheduleDataManagement, 99);
        //then
        assertThat(routePartDuration, is(nullValue()));
    }

    @Test
    public void getRouteDistance_inputCorrectValuePolylineIndexZero_assertResultDistanceIsProper() {
        //given
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        PolylineMessageContentService polylineMessageContentService = new PolylineMessageContentService();
        //when
        int routePartDistance = polylineMessageContentService.getRouteDistance(routerScheduleDataManagement, 0);
        //then
        assertThat(routePartDistance, is(equalTo(125000)));
    }

    @Test
    public void getRouteDistance_inputCorrectValuePolylineIndexOne_assertResultDistanceIsProper() {
        //given
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        PolylineMessageContentService polylineMessageContentService = new PolylineMessageContentService();
        //when
        int routePartDistance = polylineMessageContentService.getRouteDistance(routerScheduleDataManagement, 1);
        //then
        assertThat(routePartDistance, is(equalTo(371000)));
    }

    @Test
    public void getRouteDistance_inputInCorrectValuePolylineIndexOutOfRange_assertResultDistanceIsNull() {
        //given
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        PolylineMessageContentService polylineMessageContentService = new PolylineMessageContentService();
        //when
        int routePartDistance = polylineMessageContentService.getRouteDistance(routerScheduleDataManagement, 96);
        //then
        assertThat(routePartDistance, is(0));
    }

    @After
    public void correctRepoData() {
        DataSaver<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();
        routerScheduleDataManagement.save(null);
    }
}
