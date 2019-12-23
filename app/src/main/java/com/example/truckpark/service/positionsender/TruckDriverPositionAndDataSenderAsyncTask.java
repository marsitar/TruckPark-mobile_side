package com.example.truckpark.service.positionsender;

import android.os.AsyncTask;
import android.util.Log;

import com.example.truckpark.repository.CurrentPosition;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

public class TruckDriverPositionAndDataSenderAsyncTask extends AsyncTask<Void, Void, Void> {

    private String URI;
    private String className = this.getClass().getSimpleName();

    public TruckDriverPositionAndDataSenderAsyncTask(String URI) {
        this.URI = URI;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            String httpAddress = buildUrl();
            URL url = new URL(httpAddress);
            HttpURLConnection connectionToRest = getHttpURLConnection(url);

            JSONObject truckDriverWayJSON = getFullTruckDriverWayJson();
            DataOutputStream jsonOutputStream = new DataOutputStream(connectionToRest.getOutputStream());
            String truckDriverWayJSONAsString = truckDriverWayJSON.toString();
            jsonOutputStream.writeBytes(truckDriverWayJSONAsString);

            String.valueOf(connectionToRest.getResponseCode());

            jsonOutputStream.flush();
            connectionToRest.disconnect();

            Log.i(className, "truckDriverWay successfully send to the server.");
        } catch (JSONException jsonException) {
            Log.e(className, "Problem with json.");
        } catch (IOException ioexception) {
            Log.e(className, "Problem with access to data.");
        }

        return null;

    }

    private String buildUrl() {
        StringBuilder builtURL = new StringBuilder();
        builtURL.append(URI);
        builtURL.append("/");
        builtURL.append("truckdriverways");
        builtURL.append("/");
        builtURL.append("truckdriverway");

        return builtURL.toString();
    }

    private HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);

        Log.d(className, String.format("Connection: %s has been created.", conn.toString()));

        return conn;
    }

    private JSONObject getFullTruckDriverWayJson() throws JSONException {

        JSONObject coordinateJSON = generateCoordinateJsonObject();

        JSONObject truckDriverWayJSON = generateTruckDriverJsonObject(coordinateJSON);

        return truckDriverWayJSON;
    }

    private JSONObject generateTruckDriverJsonObject(JSONObject coordinateJSON) throws JSONException {

        JSONObject truckDriverWayJSON = new JSONObject();

        truckDriverWayJSON.put("id", null);
        truckDriverWayJSON.put("resultTime", LocalDateTime.now());
        truckDriverWayJSON.put("distance", 0);
        truckDriverWayJSON.put("fuel", 0);
        truckDriverWayJSON.put("driverId", 1);
        truckDriverWayJSON.put("truckId", 1);
        truckDriverWayJSON.put("coordinateDto", coordinateJSON);

        Log.v(className, String.format("Json with all truck driver way information- %s, has been generated.",truckDriverWayJSON.toString()));

        return truckDriverWayJSON;
    }

    private JSONObject generateCoordinateJsonObject() throws JSONException {

        JSONObject coordinateJSON = new JSONObject();

        coordinateJSON.put("id", null);
        coordinateJSON.put("x", CurrentPosition.getCurrentPositionInstance().getCurrentX());
        coordinateJSON.put("y", CurrentPosition.getCurrentPositionInstance().getCurrentY());

        Log.v(className, String.format("Json with coordinates- %s, has been generated.",coordinateJSON.toString()));

        return coordinateJSON;
    }

}
