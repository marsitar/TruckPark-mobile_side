package com.example.truckpark.service.positionsender;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

public class PositionSenderAsyncTask extends AsyncTask<Void, Void, Void> {

    private HttpURLConnection connectionToRest;

    private JSONObject truckDriverWayJSON;

    public PositionSenderAsyncTask(HttpURLConnection connectionToRest, JSONObject truckDriverWayJSON) {
        this.connectionToRest = connectionToRest;
        this.truckDriverWayJSON = truckDriverWayJSON;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            DataOutputStream jsonOutputStream = new DataOutputStream(connectionToRest.getOutputStream());
            String truckDriverWayJSONAsString = truckDriverWayJSON.toString();
            jsonOutputStream.writeBytes(truckDriverWayJSONAsString);

            String.valueOf(connectionToRest.getResponseCode());

            jsonOutputStream.flush();
            connectionToRest.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}
