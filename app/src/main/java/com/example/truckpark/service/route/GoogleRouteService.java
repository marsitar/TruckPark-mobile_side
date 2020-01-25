package com.example.truckpark.service.route;

import android.content.Context;
import android.util.Log;

import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.example.truckpark.properties.PropertyManager;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class GoogleRouteService {

    private final static String PROPERTY_FILE_NAME = "google-maps.properties";
    private final String APIKEY;
    private final String URI;
    private String className = this.getClass().getSimpleName();

    public GoogleRouteService(Context context) {

        PropertyManager propertyManager = new PropertyManager(PROPERTY_FILE_NAME);
        APIKEY = propertyManager.getProperty("APIKEY", context);
        URI = propertyManager.getProperty("URI", context);

    }

    public GoogleRoute getGoogleRoute(String origin, String destination) {

        GoogleRouteRequestAsyncTask googleRouteRequestAsyncTask = new GoogleRouteRequestAsyncTask(APIKEY, URI, origin, destination);
        googleRouteRequestAsyncTask.execute();

        GoogleRoute requestedGoogleRoute = null;
        try {
            requestedGoogleRoute = googleRouteRequestAsyncTask.get();
        } catch (ExecutionException executionException) {
            Log.e(className, String.format("Problem with getting result of GoogleRoute request. Origin=%s, destination=%s", origin, destination));
        } catch (InterruptedException interruptedException) {
            Log.e(className, String.format("Problem with interrupted connection during getting googleRoute data. Origin=%s, destination=%s", origin, destination));
        }

        Log.d(className, String.format("GoogleRoute request has been successfully completed. Origin=%s, destination=%s", origin, destination));

        return requestedGoogleRoute;
    }

    public List<GoogleRoute> generateGoogleRouteListFromItineraryPointPairs(List<String[]> itineraryPointPairs) {

        return itineraryPointPairs
                .stream()
                .map(originDestinationArray -> getGoogleRoute(originDestinationArray[0], originDestinationArray[1]))
                .collect(Collectors.toList());
    }

}
