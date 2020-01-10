package com.example.truckpark.service.weather;

import android.content.Context;
import android.util.Log;

import com.example.truckpark.domain.json.weatherapi.Weather;
import com.example.truckpark.properties.PropertyManager;

import java.util.concurrent.ExecutionException;

public class WeatherDataService {

    private final static String PROPERTY_FILE_NAME = "openweathermap.properties";
    private final String APIKEY;
    private final String URI;
    private String className = this.getClass().getSimpleName();

    public WeatherDataService(Context context) {

        PropertyManager propertyManager = new PropertyManager(PROPERTY_FILE_NAME);
        APIKEY = propertyManager.getProperty("APIKEY", context);
        URI = propertyManager.getProperty("URI", context);

    }

    public Weather getWeatherByCityName(String cityName) {

        WeatherDataRequestAsyncTask weatherDataRequestAsyncTask = new WeatherDataRequestAsyncTask(URI, APIKEY, cityName);
        weatherDataRequestAsyncTask.execute();

        Weather weather = null;
        try {
            weather = weatherDataRequestAsyncTask.get();
        } catch (ExecutionException executionException) {
            Log.e(className, "Problem with getting result of GoogleRoute request.");
        } catch (InterruptedException interruptedException) {
            Log.e(className, "Problem with interrupted connection during getting googleRoute data.");
        }

        Log.d(className, "Weather request has been successfully completed.");

        return weather;
    }

}
