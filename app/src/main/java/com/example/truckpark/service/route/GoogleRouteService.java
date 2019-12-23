package com.example.truckpark.service.route;

import android.content.Context;
import android.util.Log;

import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.example.truckpark.properties.PropertyManager;

import java.util.concurrent.ExecutionException;

public class GoogleRouteService {

    private final String APIKEY;
    private final String URI;
    private String className = this.getClass().getSimpleName();

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
        } catch (ExecutionException executionException) {
            Log.e(className, "Problem with getting result of GoogleRoute request.");
        } catch (InterruptedException interruptedException) {
            Log.e(className, "Problem with interrupted connection during getting googleRoute data.");
        }

        Log.d(className, "GoogleRoute request has been successfully completed.");

        return requestedGoogleRoute;
    }

}
