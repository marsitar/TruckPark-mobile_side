package com.example.truckpark.service.positionsender;

import android.os.AsyncTask;
import android.util.Log;

import com.example.truckpark.domain.json.truckdriverwayapi.CoordinateDto;
import com.example.truckpark.domain.json.truckdriverwayapi.TruckDriverWayDtoCreate;
import com.example.truckpark.exception.PositionIsNotEstablishedYetException;
import com.example.truckpark.repository.CurrentPosition;
import com.example.truckpark.view.login.LoginActivity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TruckDriverPositionAndDataSenderAsyncTask extends AsyncTask<Void, Void, Void> {

    private String URI;
    private String className = this.getClass().getSimpleName();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public TruckDriverPositionAndDataSenderAsyncTask(String URI) {
        this.URI = URI;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String url = buildUrl();
        try {
            String string = getFullTruckDriverWayJson();
            Request postRequest = buildRequest(url, string);
            doPostRequest(postRequest);
        } catch (PositionIsNotEstablishedYetException positionIsNotEstablishedYetException){
            Log.e(className, positionIsNotEstablishedYetException.getMessage());
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

    private Request buildRequest(String url, String json) {

        RequestBody body = RequestBody.create(json, JSON);

        Headers headers = new Headers.Builder()
                .add("Authorization", String.format("Bearer %s", LoginActivity.TOKEN))
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
