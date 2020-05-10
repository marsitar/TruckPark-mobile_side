package com.example.truckpark.service.positionsender;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.truckpark.domain.json.truckdriverwayapi.CoordinateDto;
import com.example.truckpark.domain.json.truckdriverwayapi.TruckDriverWayDtoCreate;
import com.example.truckpark.exception.PositionIsNotEstablishedYetException;
import com.example.truckpark.repository.CurrentPosition;
import com.example.truckpark.util.KeycloakHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jboss.aerogear.android.core.Callback;

import java.io.IOException;
import java.time.LocalDateTime;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TruckDriverPositionAndDataSenderAsyncTask extends AsyncTask<Void, Void, Void> {

    private final String URI;
    private final String className = this.getClass().getSimpleName();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static String keycloakToken;

    public TruckDriverPositionAndDataSenderAsyncTask(String URI) {
        this.URI = URI;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        if (!KeycloakHelper.isConnected()) {

            Log.d(className, "Connection with keycloak is to be established");

            KeycloakHelper.connect(new Activity(), new Callback() {
                @Override
                public void onSuccess(Object token) {
                    TruckDriverPositionAndDataSenderAsyncTask.keycloakToken = (String) token;
                    sendCurrentPosition();

                    Log.d(className, "Send position request has been successfully completed.");
                }

                @Override
                public void onFailure(Exception exception) {
                    Log.e(className, String.format("Error during sending position to remote access point. Exception: %s", exception.getMessage()));
                }
            });
        } else {
            Log.d(className, "Connection with Keycloak has been already established");
            sendCurrentPosition();
        }

        return null;
    }

    private void sendCurrentPosition(){
        String url = buildUrl();
        try {
            String string = getFullTruckDriverWayJson();
            Request postRequest = buildRequest(url, string, keycloakToken);
            doPostRequest(postRequest);
        } catch (PositionIsNotEstablishedYetException positionIsNotEstablishedYetException) {
            Log.e(className, positionIsNotEstablishedYetException.getMessage());
        }
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

    private Request buildRequest(String url, String json, Object token) {

        RequestBody body = RequestBody.create(json, JSON);

        Headers headers = new Headers.Builder()
                .add("Authorization", String.format("Bearer %s", token))
                .add("Content-Type", "application/json")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(body)
                .build();

        Log.d(className, String.format(" Request(url=%s) - %s has been built.", url, request.toString()));

        return request;
    }

    private void doPostRequest(Request request) {

        OkHttpClient client = new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {
            if(response.isSuccessful()){
                Log.d(className, String.format("PostRequest is successful. Response code=%d.", response.code()));
                Log.i(className, "truckDriverWay successfully send to the server.");
            } else {
                Log.e(className, String.format("PostRequest is failure. Response code=%d.", response.code()));
            }
        } catch (IOException ioexception) {
            Log.wtf(className, String.format("Something get wrong with PostRequest. Error - %s", ioexception));
        }
    }

    private String getFullTruckDriverWayJson() throws PositionIsNotEstablishedYetException{

        CoordinateDto coordinateDto = generateCoordinateObject();

        TruckDriverWayDtoCreate truckDriverWayDtoCreate = generateTruckDriverWayObject(coordinateDto);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(truckDriverWayDtoCreate);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private CoordinateDto generateCoordinateObject() throws PositionIsNotEstablishedYetException{

        CoordinateDto coordinateDto = new CoordinateDto();

        double lat = CurrentPosition.getCurrentPositionInstance().getCurrentLat();
        double lng = CurrentPosition.getCurrentPositionInstance().getCurrentLng();

        if(lat == 0 || lng == 0){
            throw new PositionIsNotEstablishedYetException();
        }

        coordinateDto.setLat(lat);
        coordinateDto.setLng(lng);

        return coordinateDto;
    }

    private TruckDriverWayDtoCreate generateTruckDriverWayObject(CoordinateDto coordinateDto) throws PositionIsNotEstablishedYetException{

        TruckDriverWayDtoCreate truckDriverWayDtoCreate = new TruckDriverWayDtoCreate();

        String resulTime =LocalDateTime.now().toString();

        truckDriverWayDtoCreate.setResultTime(resulTime);
        truckDriverWayDtoCreate.setDistance(0.0);
        truckDriverWayDtoCreate.setFuel(0.0);
        truckDriverWayDtoCreate.setDriverId(1L);
        truckDriverWayDtoCreate.setTruckId(1L);
        truckDriverWayDtoCreate.setCoordinateDto(coordinateDto);

        return truckDriverWayDtoCreate;
    }
}
