package com.example.truckpark.service.positionsender;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.StrictMode;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

public class SendTruckDriverPositionAndDataService extends Service {

    private final IBinder binder = new SendTruckDriverPositionAndDataBinder();

    public SendTruckDriverPositionAndDataService() {
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
                //////////////////////////
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                /////////////////////////
                URL url = new URL("http://192.168.0.21:8080/rest/api/truckdriverways/truckdriverway");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                LocalDateTime fromDateAndTime = LocalDateTime.now();

                JSONObject truckDriverWayJSON = new JSONObject();

                JSONObject coordinateJSON = new JSONObject();
                coordinateJSON.put("id", null);
                coordinateJSON.put("x", 21.1);
                coordinateJSON.put("y", 52.3);


                truckDriverWayJSON.put("id", null);
                truckDriverWayJSON.put("resultTime", LocalDateTime.now());
                truckDriverWayJSON.put("distance", 21.2);
                truckDriverWayJSON.put("fuel", 2.54);
                truckDriverWayJSON.put("driverId", 1);
                truckDriverWayJSON.put("truckId", 1);
                truckDriverWayJSON.put("coordinateDto", coordinateJSON);

                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                String string = truckDriverWayJSON.toString();
                os.writeBytes(string);
                os.flush();
                os.close();
                String.valueOf(conn.getResponseCode());
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
