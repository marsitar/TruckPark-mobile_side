package com.example.truckpark.service.weather;

import android.content.Context;

import com.example.truckpark.domain.json.weatherapi.Weather;
import com.example.truckpark.properties.PropertyManager;

import java.util.concurrent.ExecutionException;

public class WeatherDataService {

    private final String APIKEY;
    private final String URI;

    public WeatherDataService(Context context) {

        PropertyManager propertyManager = new PropertyManager("openweathermap.properties");
        APIKEY = propertyManager.getProperty("APIKEY", context);
        URI = propertyManager.getProperty("URI", context);

    }

    public Weather getWeatherByCityName(String cityName) {

        WeatherDataRequestAsyncTask weatherDataRequestAsyncTask = new WeatherDataRequestAsyncTask(URI, APIKEY, cityName);
        weatherDataRequestAsyncTask.execute();

        Weather weather = null;
        try {
            weather = weatherDataRequestAsyncTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return weather;
    }

}
