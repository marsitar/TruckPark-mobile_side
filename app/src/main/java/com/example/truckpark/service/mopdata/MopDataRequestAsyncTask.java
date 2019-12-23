package com.example.truckpark.service.mopdata;

import android.os.AsyncTask;
import android.util.Log;

import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.repository.CurrentMops;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class MopDataRequestAsyncTask extends AsyncTask<Void, Void, Void> {

    private String URI;
    private String CATEGORY;
    private String className = this.getClass().getSimpleName();

    public MopDataRequestAsyncTask(String URI, String CATEGORY) {
        this.URI = URI;
        this.CATEGORY = CATEGORY;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        ObjectMapper mapperJsonToClass = new ObjectMapper();
        String url = buildUrl(CATEGORY);
        List<Mop> mopsData = null;
        try {
            Mop[] mopsArrayData = mapperJsonToClass.readValue(new URL(url), Mop[].class);
            mopsData = Arrays.asList(mopsArrayData);
        } catch (JsonParseException | JsonMappingException jsonException) {
            Log.e(className, "Problem with json (parsing or mapping).");
        } catch (MalformedURLException malformedURLException) {
            Log.e(className, "Problem with malformed URL.");
        } catch (IOException ioexception) {
            Log.e(className, "Problem with access to data.");
        }

        Log.d(className, "Mops request has been successfully completed.");

        setCurrentMopsInRepository(mopsData);
        return null;
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

    private void setCurrentMopsInRepository(List<Mop> mopsData) {

        CurrentMops.getCurrentMopsInstance().setCurrentMopsList(mopsData);

        Log.d(className, "New mops has been set in repository.");

    }

}
