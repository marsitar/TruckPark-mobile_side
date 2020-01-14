package com.example.truckpark.service.route;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.domain.json.googledirectionsapi.LatLng;
import com.example.truckpark.domain.json.googledirectionsapi.Polyline;
import com.example.truckpark.domain.json.googledirectionsapi.Step;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class SimpleRouteServiceTest {

    private static Context context;

    @BeforeClass
    public static void prepareDataForTests() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void getSimpleRoute_inputCorrectValues_assertResultGraterThanZero() {
        //given
        SimpleRouteService simpleRouteService = new SimpleRouteService();
        //when
        List<Double[]> simpleRoute = simpleRouteService.getSimpleRoute("Warszawa", "Poznan", context);
        int simpleRouteSize = simpleRoute.size();
        //then
        assertThat(simpleRouteSize, is(greaterThan(0)));
    }

    @Test
    public void getSimpleRoute_inputIncorrectValues_assertResultIsZero() {
        //given
        SimpleRouteService simpleRouteService = new SimpleRouteService();
        //when
        List<Double[]> simpleRoute = simpleRouteService.getSimpleRoute("Warszawa", "wqe23sd", context);
        int simpleRouteSize = simpleRoute.size();
        //then
        assertThat(simpleRouteSize, is(0));
    }

    @Test
    public void getSimpleRoute_inputNullValue_assertResultIsZero() {
        //given
        SimpleRouteService simpleRouteService = new SimpleRouteService();
        //when
        List<Double[]> simpleRoute = simpleRouteService.getSimpleRoute(null, "wqe23sd", context);
        int simpleRouteSize = simpleRoute.size();
        //then
        assertThat(simpleRouteSize, is(0));
    }

    @Test
    public void fillPointsList_inputCorrectValues_assertResultGraterThanZero() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        //given
        SimpleRouteService simpleRouteService = new SimpleRouteService();
        List<Double[]> points = new ArrayList<>();
        List<Step> steps = new ArrayList<>();

        Polyline polyline1 = new Polyline();
        polyline1.setPoints("__x}Hsqg_C`@SZMBABAB@@?@@BB@B@?@?f@S");
        LatLng latLng1 = new LatLng(52.2291168, 21.015462);
        LatLng latLng2 = new LatLng(52.2284848, 21.0156871);

        Polyline polyline2 = new Polyline();
        polyline2.setPoints("_{w}Hasg_Ca@_DGo@SeBEY");
        LatLng latLng3 = new LatLng(52.2284848, 21.0156871);
        LatLng latLng4 = new LatLng(52.228821, 21.0173684);

        Step step1 = new Step();
        step1.setPolyline(polyline1);
        step1.setStartLocation(latLng1);
        step1.setEndLocation(latLng2);

        Step step2 = new Step();
        step2.setPolyline(polyline2);
        step2.setStartLocation(latLng3);
        step2.setEndLocation(latLng4);

        steps.add(step1);
        steps.add(step2);
        //when
        getFillPointsListMethod(simpleRouteService, steps, points);
        //then
        assertThat(steps.isEmpty(), is(false));
    }

    @Test
    public void fillPointsList_inputEmptyList_assertResultListIsEmpty() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        //given
        SimpleRouteService simpleRouteService = new SimpleRouteService();
        List<Double[]> points = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        //when
        getFillPointsListMethod(simpleRouteService, steps, points);
        //then
        assertThat(steps.isEmpty(), is(true));
    }

    @Test
    public void fillPointsList_inputNullValue_assertResultListIsEmpty() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        //given
        SimpleRouteService simpleRouteService = new SimpleRouteService();
        List<Double[]> points = new ArrayList<>();
        List<Step> steps = null;
        //when
        getFillPointsListMethod(simpleRouteService, steps, points);
        //then
        assertThat(steps, is(nullValue(List.class)));
    }

    private void getFillPointsListMethod(SimpleRouteService simpleRouteService, List<Step> steps, List<Double[]> points) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Class simpleRouteServiceClass = simpleRouteService.getClass();
        Method fillPointsListMethod = simpleRouteServiceClass.getDeclaredMethod("fillPointsList", List.class, List.class);
        fillPointsListMethod.setAccessible(true);

        fillPointsListMethod.invoke(simpleRouteService, steps , points );
    }

}