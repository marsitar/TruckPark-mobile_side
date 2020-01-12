package com.example.truckpark.service.weather;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.domain.json.weatherapi.Weather;
import com.example.truckpark.properties.PropertyManager;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class WeatherDataRequestAsyncTaskTest {

    private final static String PROPERTY_FILE_NAME = "openweathermap.properties";
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
    public void doInBackground_inputCorrectValue_assertIsNotNull() throws ExecutionException, InterruptedException {
        //given
        WeatherDataRequestAsyncTask weatherDataRequestAsyncTask = new WeatherDataRequestAsyncTask(URI, APIKEY, "Gdansk");
        //when
        Weather weather = weatherDataRequestAsyncTask.execute().get();
        //then
        assertThat(weather, is(notNullValue()));
    }

    @Test
    public void doInBackground_inputNullValue_assertIsNull() throws ExecutionException, InterruptedException {
        //given
        WeatherDataRequestAsyncTask weatherDataRequestAsyncTask = new WeatherDataRequestAsyncTask(URI, APIKEY, null);
        //when
        Weather weather = weatherDataRequestAsyncTask.execute().get();
        //then
        assertThat(weather, is(nullValue(Weather.class)));
    }

    @Test
    public void doInBackground_inputEmptyValue_assertIsNull() throws ExecutionException, InterruptedException {
        //given
        WeatherDataRequestAsyncTask weatherDataRequestAsyncTask = new WeatherDataRequestAsyncTask(URI, APIKEY, null);
        //when
        Weather weather = weatherDataRequestAsyncTask.execute().get();
        //then
        assertThat(weather, is(nullValue(Weather.class)));
    }

    @Test
    public void buildUrl_inputCorrectValue_assertIsEqual() throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        //given
        WeatherDataRequestAsyncTask weatherDataRequestAsyncTask = new WeatherDataRequestAsyncTask(URI, APIKEY, "Gdansk");
        String correctResult = "https://api.openweathermap.org/data/2.5/weather?q=Gdansk&APPID=4e9977b23cccfda61c1aea94fabff981&units=metric";
        //when
        String builtURLMethodResult = getUrlFromMethod(weatherDataRequestAsyncTask, "Gdansk");
        //then
        assertThat(correctResult,equalTo(builtURLMethodResult));
    }

    @Test
    public void buildUrl_inputIncorrectValue_assertIsNotEqual() throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        //given
        WeatherDataRequestAsyncTask weatherDataRequestAsyncTask = new WeatherDataRequestAsyncTask(URI, APIKEY, "Gdansk");
        String correctResult = "https://api.openweathermap.org/data/2.5/weather?q=Gdansk&APPID=4e9977b23cccfda61c1aea94fabff981&units=metric";
        //when
        String builtURLMethodResult = getUrlFromMethod(weatherDataRequestAsyncTask, "Gdynsk");
        //then
        assertThat(correctResult,is(not(equalTo(builtURLMethodResult))));
    }

    private String getUrlFromMethod(WeatherDataRequestAsyncTask weatherDataRequestAsyncTask, String place) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Class weatherDataRequestAsyncTaskClass = weatherDataRequestAsyncTask.getClass();
        Method buildURLMethod = weatherDataRequestAsyncTaskClass.getDeclaredMethod("buildUrl", String.class);
        buildURLMethod.setAccessible(true);

        return (String) buildURLMethod.invoke(weatherDataRequestAsyncTask, place);
    }
}
