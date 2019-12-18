package com.example.truckpark.service.positionsender;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.truckpark.properties.PropertyManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

public class SendTruckDriverPositionAndDataService extends Service {

    private final String APIKEY;
    private final String URI;

    private final IBinder binder = new SendTruckDriverPositionAndDataBinder();

    public SendTruckDriverPositionAndDataService() {
        PropertyManager propertyManager = new PropertyManager("truckparkserver.properties");
        APIKEY= propertyManager.getProperty("APIKEY", this);
        URI= propertyManager.getProperty("URI", this);
    }

    public class SendTruckDriverPositionAndDataBinder extends Binder {
        public SendTruckDriverPositionAndDataService getSendTruckDriverPositionAndData() {
            return SendTruckDriverPositionAndDataService.this;
        }
    }

    @Override
    public void onCreate() {
        sendPost();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void sendPost() {
        Thread thread = new Thread(() -> {
            try {

                String httpAdress = buildUrl();
                URL url = new URL(httpAdress);
                HttpURLConnection connectionToRest = getHttpURLConnection(url);

                JSONObject truckDriverWayJSON = createTruckDriverWayJson();
                DataOutputStream jsonOutputStream = new DataOutputStream(connectionToRest.getOutputStream());
                String truckDriverWayJSONAsString = truckDriverWayJSON.toString();
                jsonOutputStream.writeBytes(truckDriverWayJSONAsString);
                String.valueOf(connectionToRest.getResponseCode());

                jsonOutputStream.flush();
                connectionToRest.disconnect();

            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            } catch (JSONException jsone) {
                jsone.printStackTrace();
            }
        });
        thread.start();
    }

    private String buildUrl(){
        StringBuilder buildedURL = new StringBuilder();
        buildedURL.append(URI);
        buildedURL.append("/");
        buildedURL.append("truckdriverways");
        buildedURL.append("/");
        buildedURL.append("truckdriverway");

        return buildedURL.toString();
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
