package com.example.truckpark.service.route;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.example.truckpark.properties.PropertyManager;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class SimpleRouteServiceTest {

    private final static String PROPERTY_FILE_NAME = "google-maps.properties";
    private static String APIKEY;
    private static String URI;

    @BeforeClass
    public static void prepareDataForTests() {
        Context context = InstrumentationRegistry.getTargetContext();
        PropertyManager propertyManager = new PropertyManager(PROPERTY_FILE_NAME);
        APIKEY = propertyManager.getProperty("APIKEY", context);
        URI = propertyManager.getProperty("URI", context);
    }

    @Test
    public void getSimpleRoute_inputCorrectStatusValue_assertIsEqual() throws ExecutionException, InterruptedException {
        //given
        GoogleRouteRequestAsyncTask googleRouteRequestAsyncTask = new GoogleRouteRequestAsyncTask(APIKEY, URI, "Gdansk", "Grudziadz");
        String correctStatus = "OK";
        //when
        GoogleRoute googleRoute = googleRouteRequestAsyncTask.execute().get();
        String resultStatus = googleRoute.getStatus();
        //then
        assertThat(resultStatus, equalTo(correctStatus));
    }

    @Test
    public void getSimpleRoute_inputNullStatusValue_assertIsEqual() throws ExecutionException, InterruptedException {
        //given
        GoogleRouteRequestAsyncTask googleRouteRequestAsyncTask = new GoogleRouteRequestAsyncTask(APIKEY, URI, null, "Grudziadz");
        String correctStatus = "INVALID_REQUEST";
        //when
        GoogleRoute googleRoute = googleRouteRequestAsyncTask.execute().get();
        String resultStatus = googleRoute.getStatus();
        //then
        assertThat(resultStatus, equalTo(correctStatus));
    }

    @Test
    public void buildUrl_inputCorrectValue_assertIsEqual() throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        //given
        GoogleRouteRequestAsyncTask googleRouteRequestAsyncTask = new GoogleRouteRequestAsyncTask(APIKEY, URI, "Gdansk", "Grudziadz");
        String correctResult = "https://maps.googleapis.com/maps/api/directions/json?origin=Gdansk&destination=Grudziadz&key=AIzaSyAJUIOj5zGHjGAuDGcM--yVZQIQtzDgxwI";
        //when
        String builtURLMethodResult = getUrlFromMethod(googleRouteRequestAsyncTask, "Gdansk", "Grudziadz");
        //then
        assertThat(builtURLMethodResult, equalTo(correctResult));
    }

    @Test
    public void buildUrl_inputIncorrectValue_assertIsNotEqual() throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        //given
        GoogleRouteRequestAsyncTask googleRouteRequestAsyncTask = new GoogleRouteRequestAsyncTask(APIKEY, URI, "Gdansk", "Grudziadz");
        String correctResult = "https://maps.googleapis.com/maps/api/directions/json?origin=Gdansk&destination=Grudziadz&key=AIzaSyAJUIOj5zGHjGAuDGcM--yVZQIQtzDgxwI";
        //when
        String builtURLMethodResult = getUrlFromMethod(googleRouteRequestAsyncTask, null, "Grudziadz");
        //then
        assertThat(builtURLMethodResult, is(not(equalTo(correctResult))));
    }

    private String getUrlFromMethod(GoogleRouteRequestAsyncTask googleRouteRequestAsyncTask, String origin, String destination) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Class googleRouteRequestAsyncTaskClass = googleRouteRequestAsyncTask.getClass();
        Method buildURLMethod = googleRouteRequestAsyncTaskClass.getDeclaredMethod("buildUrl", String.class);
        buildURLMethod.setAccessible(true);

        return (String) buildURLMethod.invoke(googleRouteRequestAsyncTaskClass, origin, destination);
    }
}