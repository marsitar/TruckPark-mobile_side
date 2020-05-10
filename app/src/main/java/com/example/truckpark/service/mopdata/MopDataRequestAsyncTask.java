package com.example.truckpark.service.mopdata;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.localdatamanagment.DataSaver;
import com.example.truckpark.localdatamanagment.MopsDataManagement;
import com.example.truckpark.util.KeycloakHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jboss.aerogear.android.core.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MopDataRequestAsyncTask extends AsyncTask<Void, Void, List<Mop>> {

    private final String URI;
    private final String CATEGORY;
    private final String className = this.getClass().getSimpleName();
    public static String keycloakToken;
    private List<Mop> mopsData = new ArrayList<>();

    public MopDataRequestAsyncTask(String URI, String CATEGORY) {
        this.URI = URI;
        this.CATEGORY = CATEGORY;
    }

    @Override
    protected List<Mop> doInBackground(Void... voids) {

        if (!KeycloakHelper.isConnected()) {

            Log.d(className, "Connection with keycloak is to be established");

            KeycloakHelper.connect(new Activity(), new Callback() {
                @Override
                public void onSuccess(Object token) {
                    MopDataRequestAsyncTask.keycloakToken = (String) token;
                    getMopsFromRemoteSource();

                    Log.d(className, "Mops request has been successfully completed.");
                }

                @Override
                public void onFailure(Exception exception) {

                    Log.e(className, String.format("Error during getting mops from remote access point. Exception: %s", exception.getMessage()));
                }
            });
        } else {
            Log.d(className, "Connection with Keycloak has been already established");
            getMopsFromRemoteSource();
        }

        setCurrentMopsInRepository(mopsData);
        return mopsData;
    }

    private void getMopsFromRemoteSource(){
        String url = buildUrl(CATEGORY);
        Request request = buildRequest(url, MopDataRequestAsyncTask.keycloakToken);
        mopsData = doGetRequestAndMapResults(request);
    }

    private String buildUrl(String category) {

        StringBuilder builtURL = new StringBuilder();

        builtURL.append(URI);
        builtURL.append("/");
        builtURL.append(category);
        builtURL.append("/");
        builtURL.append("all");

        return builtURL.toString();
    }

    private Request buildRequest(String url, Object token) {

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", String.format("Bearer %s", token))
                .build();

        Log.d(className, String.format(" Request(url=%s) - %s has been built.", url, request.toString()));

        return request;
    }

    private List<Mop> doGetRequestAndMapResults(Request request) {

        ObjectMapper mapperJsonToClass = new ObjectMapper();
        OkHttpClient client = new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {
            String responseString = response.body().string();
            Mop[] mopsArrayData = mapperJsonToClass.readValue(responseString, Mop[].class);
            return Arrays.asList(mopsArrayData);
        } catch (IOException e) {
            Log.e(className, String.format("Problem with access to data. Request =%s", request.toString()));
            return new ArrayList<>();
        }
    }

    private void setCurrentMopsInRepository(List<Mop> mopsData) {

        Optional.ofNullable(mopsData)
            .ifPresent(mops -> {
                        DataSaver<List<Mop>> mopsDataManagement = new MopsDataManagement();
                        mopsDataManagement.save(mopsData);
                        Log.d(className, "New mops has been set in repository.");
                    }
            );
    }

}
