package com.example.truckpark.service.positionsender;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

public class TruckDriverPositionAndDataSenderAsyncTask extends AsyncTask<Void, Void, Void> {

    private String URI;

    public TruckDriverPositionAndDataSenderAsyncTask(String URI) {
        this.URI = URI;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            String httpAddress = buildUrl();
            URL url = new URL(httpAddress);
            HttpURLConnection connectionToRest = getHttpURLConnection(url);
            JSONObject truckDriverWayJSON = createTruckDriverWayJson();
            DataOutputStream jsonOutputStream = new DataOutputStream(connectionToRest.getOutputStream());
            String truckDriverWayJSONAsString = truckDriverWayJSON.toString();
            jsonOutputStream.writeBytes(truckDriverWayJSONAsString);

            String.valueOf(connectionToRest.getResponseCode());

            jsonOutputStream.flush();
            connectionToRest.disconnect();
        } catch (JSONException jsone) {
            jsone.printStackTrace();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
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
        return conn;
    }

    private JSONObject createTruckDriverWayJson() throws JSONException {

        JSONObject coordinateJSON = new JSONObject();

        coordinateJSON.put("id", null);
        coordinateJSON.put("x", 21.1);
        coordinateJSON.put("y", 52.3);

        JSONObject truckDriverWayJSON = new JSONObject();

        truckDriverWayJSON.put("id", null);
        truckDriverWayJSON.put("resultTime", LocalDateTime.now());
        truckDriverWayJSON.put("distance", 21.2);
        truckDriverWayJSON.put("fuel", 2.54);
        truckDriverWayJSON.put("driverId", 1);
        truckDriverWayJSON.put("truckId", 1);
        truckDriverWayJSON.put("coordinateDto", coordinateJSON);

        return truckDriverWayJSON;
    }

}
