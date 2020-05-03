package com.example.truckpark.service.mopdata;

import android.os.AsyncTask;
import android.util.Log;

import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.localdatamanagment.DataSaver;
import com.example.truckpark.localdatamanagment.MopsDataManagement;
import com.example.truckpark.view.login.LoginActivity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MopDataRequestAsyncTask extends AsyncTask<Void, Void, List<Mop>> {

    private String URI;
    private String CATEGORY;
    private String className = this.getClass().getSimpleName();

    public MopDataRequestAsyncTask(String URI, String CATEGORY) {
        this.URI = URI;
        this.CATEGORY = CATEGORY;
    }

    @Override
    protected List<Mop> doInBackground(Void... voids) {

        String url = buildUrl(CATEGORY);
        Request request = buildRequest(url);
        List<Mop> mopsData = doGetRequestAndMapResults(request);

        Log.d(className, String.format("Mops request has been successfully completed. Requested url=%s", url));

        setCurrentMopsInRepository(mopsData);
        return mopsData;
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

    private Request buildRequest(String url) {

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", String.format("Bearer %s", LoginActivity.TOKEN))
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
