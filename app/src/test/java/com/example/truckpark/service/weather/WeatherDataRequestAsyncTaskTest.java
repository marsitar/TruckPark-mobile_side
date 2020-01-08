package com.example.truckpark.service.weather;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class WeatherDataRequestAsyncTaskTest {

    private static String APIKEY;
    private static String URI;

    @BeforeClass
    public static void prepareDataForTests() {
        URI = "https://api.openweathermap.org/data/2.5/weather?";
        APIKEY = "4e9977b23cccfda61c1aea94fabff981";
    }

    @Test
    public void buildURLGenerateCorrectValue() throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        WeatherDataRequestAsyncTask weatherDataRequestAsyncTask = new WeatherDataRequestAsyncTask(URI, APIKEY, "Gdansk");

        Class weatherDataRequestAsyncTaskClass = weatherDataRequestAsyncTask.getClass();
        Method buildURLMethod = weatherDataRequestAsyncTaskClass.getDeclaredMethod("buildUrl", String.class);
        buildURLMethod.setAccessible(true);
        String builtURLMethodResult = (String) buildURLMethod.invoke(weatherDataRequestAsyncTask, "Gdansk");

        String correctResult = "https://api.openweathermap.org/data/2.5/weather?q=Gdansk&APPID=4e9977b23cccfda61c1aea94fabff981&units=metric";

        assertThat(correctResult,equalTo(builtURLMethodResult));
    }

}
