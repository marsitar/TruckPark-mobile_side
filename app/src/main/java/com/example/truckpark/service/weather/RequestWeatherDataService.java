package com.example.truckpark.service.weather;

import android.content.Context;

import com.example.truckpark.domain.json.weatherapi.Weather;
import com.example.truckpark.properties.PropertyManager;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestWeatherDataService {

    private final String APIKEY;
    private final String URI;

    public RequestWeatherDataService(Context context) {

        PropertyManager propertyManager = new PropertyManager("openweathermap.properties");
        APIKEY= propertyManager.getProperty("APIKEY", context);
        URI = propertyManager.getProperty("URI", context);

    }

    public Weather getWeatherByCityName(String cityName) {

        ObjectMapper mapperJsonToClass = new ObjectMapper();
        String url = buildUrl(cityName);

        Weather weather = null;

        try {
            weather = mapperJsonToClass.readValue(new URL(url), Weather.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return weather;
    }

    private String buildUrl(String cityName) {

        StringBuilder buildedURL = new StringBuilder();

        buildedURL.append(URI);
        buildedURL.append("q=");
        buildedURL.append(cityName);
        buildedURL.append("&");
        buildedURL.append("APPID=");
        buildedURL.append(APIKEY);
        buildedURL.append("&");
        buildedURL.append("units=");
        buildedURL.append("metric");

        return buildedURL.toString();
    }

}
