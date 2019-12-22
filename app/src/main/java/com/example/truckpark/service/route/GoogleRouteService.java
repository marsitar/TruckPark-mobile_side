package com.example.truckpark.service.route;

import android.content.Context;

import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.example.truckpark.properties.PropertyManager;

import java.util.concurrent.ExecutionException;

public class GoogleRouteService {

    private final String APIKEY;
    private final String URI;

    GoogleRouteService(Context context) {

        PropertyManager propertyManager = new PropertyManager("google-maps.properties");
        APIKEY = propertyManager.getProperty("APIKEY", context);
        URI = propertyManager.getProperty("URI", context);

    }

    public GoogleRoute getGoogleRoute(String origin, String destination) {

        GoogleRouteRequestAsyncTask googleRouteRequestAsyncTask = new GoogleRouteRequestAsyncTask(APIKEY, URI, origin, destination);
        googleRouteRequestAsyncTask.execute();

        GoogleRoute requestedGoogleRoute = null;
        try {
            requestedGoogleRoute = googleRouteRequestAsyncTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return requestedGoogleRoute;
    }

}
