package com.example.truckpark.service.route;

import android.content.Context;

import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.example.truckpark.properties.PropertyManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class RequestGoogleRouteService {

    private final String APIKEY;
    private final String URI;

    RequestGoogleRouteService(Context context) {

        PropertyManager propertyManager = new PropertyManager("google-maps.properties");
        APIKEY = propertyManager.getProperty("APIKEY", context);
        URI = propertyManager.getProperty("URI", context);

    }

    public GoogleRoute getGoogleRoute(String origin, String destination) {

        ObjectMapper mapperJsonToClass = new ObjectMapper();
        String url = buildUrl(origin, destination);
        GoogleRoute requestedGoogleRoute = null;
        try {
            requestedGoogleRoute = mapperJsonToClass.readValue(new URL(url), GoogleRoute.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestedGoogleRoute;
    }

    private String buildUrl(String origin, String destination) {

        StringBuilder builtURL = new StringBuilder();

        builtURL.append(URI);
        builtURL.append("origin=");
        builtURL.append(origin);
        builtURL.append("&");
        builtURL.append("destination=");
        builtURL.append(destination);
        builtURL.append("&");
        builtURL.append("key=");
        builtURL.append(APIKEY);

        return builtURL.toString();
    }
}
