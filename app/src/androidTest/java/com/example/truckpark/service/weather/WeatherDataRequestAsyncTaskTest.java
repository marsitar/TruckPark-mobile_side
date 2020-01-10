package com.example.truckpark.service.weather;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.properties.PropertyManager;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class WeatherDataRequestAsyncTaskTest {

    private static String APIKEY;
    private static String URI;

    @BeforeClass
    public static void prepareDataForTests() {
        Context context = InstrumentationRegistry.getTargetContext();
        PropertyManager propertyManager = new PropertyManager("openweathermap.properties");
        APIKEY = propertyManager.getProperty("APIKEY", context);
        URI = propertyManager.getProperty("URI", context);
    }

    @Test
    public void buildURLGenerateCorrectValue() throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        WeatherDataRequestAsyncTask weatherDataRequestAsyncTask = new WeatherDataRequestAsyncTask(URI, APIKEY, "Gdansk");

        String builtURLMethodResult = getURLfromMethod(weatherDataRequestAsyncTask, "Gdansk");

        String correctResult = "https://api.openweathermap.org/data/2.5/weather?q=Gdansk&APPID=4e9977b23cccfda61c1aea94fabff981&units=metric";

        assertThat(correctResult,equalTo(builtURLMethodResult));
    }

    @Test
    public void buildURLGenerateInCorrectValue() throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        WeatherDataRequestAsyncTask weatherDataRequestAsyncTask = new WeatherDataRequestAsyncTask(URI, APIKEY, "Gdansk");

        String builtURLMethodResult = getURLfromMethod(weatherDataRequestAsyncTask, "Gdynsk");

        String correctResult = "https://api.openweathermap.org/data/2.5/weather?q=Gdansk&APPID=4e9977b23cccfda61c1aea94fabff981&units=metric";

        assertThat(correctResult,is(not(equalTo(builtURLMethodResult))));
    }

    private String getURLfromMethod(WeatherDataRequestAsyncTask weatherDataRequestAsyncTask, String place) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class weatherDataRequestAsyncTaskClass = weatherDataRequestAsyncTask.getClass();
        Method buildURLMethod = weatherDataRequestAsyncTaskClass.getDeclaredMethod("buildUrl", String.class);
        buildURLMethod.setAccessible(true);
        return (String) buildURLMethod.invoke(weatherDataRequestAsyncTask, place);
    }
}
