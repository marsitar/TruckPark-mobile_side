package com.example.truckpark.service.route;

import android.os.AsyncTask;
import android.util.Log;

import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class GoogleRouteRequestAsyncTask extends AsyncTask<Void, Void, GoogleRoute> {

    private String APIKEY;
    private String URI;
    private String origin;
    private String destination;
    private String className = this.getClass().getSimpleName();

    public GoogleRouteRequestAsyncTask(String APIKEY, String URI, String origin, String destination) {
        this.APIKEY = APIKEY;
        this.URI = URI;
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    protected GoogleRoute doInBackground(Void... voids) {
        ObjectMapper mapperJsonToClass = new ObjectMapper();
        String url = buildUrl(origin, destination);
        GoogleRoute requestedGoogleRoute = null;
        try {
            requestedGoogleRoute = mapperJsonToClass.readValue(new URL(url), GoogleRoute.class);
        } catch (IOException ioexception) {
            Log.e(className, "Problem with access to data.");
        }

        Log.d(className, "GoogleRoute request has been successfully completed");

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
