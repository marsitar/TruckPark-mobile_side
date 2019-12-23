package com.example.truckpark.service.weather;

import android.os.AsyncTask;
import android.util.Log;

import com.example.truckpark.domain.json.weatherapi.Weather;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherDataRequestAsyncTask extends AsyncTask<Void, Void, Weather> {

    private String URI;
    private String APIKEY;
    private String cityName;
    private String className = this.getClass().getSimpleName();

    public WeatherDataRequestAsyncTask(String URI, String APIKEY, String cityName) {
        this.URI = URI;
        this.APIKEY = APIKEY;
        this.cityName = cityName;
    }

    @Override
    protected Weather doInBackground(Void... voids) {

        ObjectMapper mapperJsonToClass = new ObjectMapper();
        String url = buildUrl(cityName);

        Weather weather = null;
        try {
            weather = mapperJsonToClass.readValue(new URL(url), Weather.class);
        } catch (JsonParseException | JsonMappingException jsonException) {
            Log.e(className, "Problem with json (parsing or mapping).");
        } catch (MalformedURLException malformedURLException) {
            Log.e(className, "Problem with malformed URL.");
        } catch (IOException ioexception) {
            Log.e(className, "Problem with access to data.");
        }

        Log.d(className, "Weather request has been successfully completed.");

        return weather;
    }

    private String buildUrl(String cityName) {

        StringBuilder builtURL = new StringBuilder();

        builtURL.append(URI);
        builtURL.append("q=");
        builtURL.append(cityName);
        builtURL.append("&");
        builtURL.append("APPID=");
        builtURL.append(APIKEY);
        builtURL.append("&");
        builtURL.append("units=");
        builtURL.append("metric");

        return builtURL.toString();
    }
}
