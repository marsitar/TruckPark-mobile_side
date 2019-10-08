package com.example.truckpark.service;

import com.example.truckpark.domain.json.GoogleDirectionsApi.GoogleRoute;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class RequestGoogleRouteService {

    private static final String APIKEY = "AIzaSyCNNt4ExpvKv0uHmFSwDtJ-Y3d3XtviRqg";
    private static final String URI = "https://maps.googleapis.com/maps/api/directions/json?";

    public GoogleRoute getGoogleRoute(String origin, String destination){

        ObjectMapper mapperJsonToClass = new ObjectMapper();
        String url = buildUrl(origin,destination);
        GoogleRoute requestedGoogleRoute = null;
        try {
            requestedGoogleRoute = mapperJsonToClass.readValue(url, GoogleRoute.class);
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
        buildedURL.append("destionation=");
        buildedURL.append(destination);
        buildedURL.append("&");
        buildedURL.append("key=");
        buildedURL.append(APIKEY);

        return buildedURL.toString();
    }
}
