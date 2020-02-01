package com.example.truckpark.service.routeschedule;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.domain.entity.RoutePart;
import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.example.truckpark.service.route.GoogleRouteService;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class RouteScheduleServiceTest {

    private static Context context;
    private static RouteScheduleService routeScheduleService;

    @BeforeClass
    public static void prepareDataForTests() {
        context = InstrumentationRegistry.getTargetContext();
        routeScheduleService = new RouteScheduleService(context);
    }

    @Test
    public void prepareRouteSchedule_inputCorrectItineraryPoints_resultInstanceFieldIsNotNull() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        //given
        List<String> itineraryPoints = Arrays.asList("gdansk", "gdynia", "grudziadz");
        //when
        RouteSchedule routeSchedule = prepareRouteScheduleFromMethod(itineraryPoints);
        //then
        assertThat(routeSchedule.getSaveDateAndTime(), is(notNullValue()));
    }

    @Test
    public void getItineraryPointPairsFromItineraryPoints_inputCorrectItineraryPoints_itemNumbersAreCorrect() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        //given
        List<String> itineraryPoints = Arrays.asList("warszawa", "gdynia", "bialystok");
        Integer two = 2;
        //when
        List<String[]> itineraryPointPairsFromItineraryPoints = getItineraryPointPairsFromItineraryPointsFromMethod(itineraryPoints);
        //then
        assertThat(itineraryPointPairsFromItineraryPoints.size(), comparesEqualTo(two));
    }

    @Test
    public void getRoutePartsFromGoogleRoutes_inputCorrectGoogleRoutes_itemNumbersAreGreaterThanZero() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        //given
        GoogleRouteService googleRouteService = new GoogleRouteService(context);
        List<String[]> itineraryPointPairs = Arrays.asList(new String[]{"warszawa", "gdynia"}, new String[]{"gdynia", "bialystok"});
        List<GoogleRoute> generatedGoogleRouts = googleRouteService.generateGoogleRouteListFromItineraryPointPairs(itineraryPointPairs);
        Integer zero = 0;
        //when
        List<RoutePart> routePartsFromGoogleRoutes = getRoutePartsFromGoogleRoutesFromMethod(generatedGoogleRouts);
        //then
        assertThat(routePartsFromGoogleRoutes.size(), is(greaterThan(zero)));
    }

    private RouteSchedule prepareRouteScheduleFromMethod(List<String> itineraryPoints) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class routeScheduleServiceClass = routeScheduleService.getClass();
        Method prepareRouteScheduleMethod = routeScheduleServiceClass.getDeclaredMethod("prepareRouteSchedule", List.class);
        prepareRouteScheduleMethod.setAccessible(true);

        return (RouteSchedule) prepareRouteScheduleMethod.invoke(routeScheduleService, itineraryPoints);
    }

    private List<String[]> getItineraryPointPairsFromItineraryPointsFromMethod(List<String> itineraryPoints) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class routeScheduleServiceClass = routeScheduleService.getClass();
        Method getItineraryPointPairsFromItineraryPointsMethod = routeScheduleServiceClass.getDeclaredMethod("getItineraryPointPairsFromItineraryPoints", List.class);
        getItineraryPointPairsFromItineraryPointsMethod.setAccessible(true);

        return (List<String[]>) getItineraryPointPairsFromItineraryPointsMethod.invoke(routeScheduleService, itineraryPoints);
    }

    private List<RoutePart> getRoutePartsFromGoogleRoutesFromMethod(List<GoogleRoute> generatedGoogleRouts) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class routeScheduleServiceClass = routeScheduleService.getClass();
        Method getRoutePartsFromGoogleRoutesMethod = routeScheduleServiceClass.getDeclaredMethod("getRoutePartsFromGoogleRoutes", List.class);
        getRoutePartsFromGoogleRoutesMethod.setAccessible(true);

        return (List<RoutePart>) getRoutePartsFromGoogleRoutesMethod.invoke(routeScheduleService, generatedGoogleRouts);

    }
}
