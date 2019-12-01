package com.example.truckpark.service;

import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.example.truckpark.properties.PropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class RequestGoogleRouteService {

    private static final String APIKEY;
    private static final String URI;

    static {
        PropertyService propertyService = new PropertyService("/assets/google-maps.properties");
        APIKEY=propertyService.getProperty("APIKEY");
        URI=propertyService.getProperty("URI");
    }

    public GoogleRoute getGoogleRoute(String origin, String destination){

        ObjectMapper mapperJsonToClass = new ObjectMapper();
        String url = buildUrl(origin,destination);
        GoogleRoute requestedGoogleRoute = null;
        try {
            requestedGoogleRoute = mapperJsonToClass.readValue(new URL(url), GoogleRoute.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestedGoogleRoute;
    }

    private String buildUrl(String origin, String destination){
        StringBuilder buildedURL = new StringBuilder();
        buildedURL.append(URI);
        buildedURL.append("origin=");
        buildedURL.append(origin);
        buildedURL.append("&");
        buildedURL.append("destination=");
        buildedURL.append(destination);
        buildedURL.append("&");
        buildedURL.append("key=");
        buildedURL.append(APIKEY);

        return buildedURL.toString();
    }
}
