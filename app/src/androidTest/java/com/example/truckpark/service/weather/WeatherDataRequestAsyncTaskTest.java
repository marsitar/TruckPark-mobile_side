package com.example.truckpark.service.weather;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.domain.json.weatherapi.Weather;
import com.example.truckpark.properties.PropertyManager;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

@RunWith(AndroidJUnit4.class)
public class WeatherDataRequestAsyncTaskTest {

    private String APIKEY;
    private String URI;

    @BeforeClass
    public void prepareDataForTests() {
        Context context = InstrumentationRegistry.getTargetContext();
        PropertyManager propertyManager = new PropertyManager("openweathermap.properties");
        APIKEY = propertyManager.getProperty("APIKEY", context);
        URI = propertyManager.getProperty("URI", context);
    }

    @Test
    public void weatherDataRequestAsyncTaskGeneratedClassNotNull() throws InterruptedException, ExecutionException {
        WeatherDataRequestAsyncTask weatherDataRequestAsyncTask = new WeatherDataRequestAsyncTask(URI, APIKEY, "Gdansk");
        Weather weatherInGdansk = weatherDataRequestAsyncTask.execute().get();

        Assert.assertNotNull(weatherInGdansk);
    }

    @Test
    public void buildURLGenerateCorrectValue() throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        WeatherDataRequestAsyncTask weatherDataRequestAsyncTask = new WeatherDataRequestAsyncTask(URI, APIKEY, "Gdansk");

        Class weatherDataRequestAsyncTaskClass = weatherDataRequestAsyncTask.getClass();
        Method buildURLMethod = weatherDataRequestAsyncTaskClass.getDeclaredMethod("buildUrl", String.class);
        buildURLMethod.setAccessible(true);
        String builtURLMethodResult = (String) buildURLMethod.invoke(weatherDataRequestAsyncTaskClass, "Gdansk");

        String correctResult = "https://api.openweathermap.org/data/2.5/weather?q=Gdansk&APPID=4e9977b23cccfda61c1aea94fabff981&units=metric";
        Assert.assertEquals(correctResult,builtURLMethodResult);
    }

}
