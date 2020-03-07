package com.example.truckpark.service.mopdata;

import android.os.AsyncTask;
import android.util.Log;

import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.localdatamanagment.DataSaver;
import com.example.truckpark.localdatamanagment.MopsDataManagement;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

        ObjectMapper mapperJsonToClass = new ObjectMapper();

        String url = buildUrl(CATEGORY);
        List<Mop> mopsData = null;
        try {
            Mop[] mopsArrayData = mapperJsonToClass.readValue(new URL(url), Mop[].class);
            mopsData = Arrays.asList(mopsArrayData);
        } catch (JsonParseException | JsonMappingException jsonException) {
            Log.e(className, String.format("Problem with json (parsing or mapping). Requested url=%s", url));
        } catch (MalformedURLException malformedURLException) {
            Log.e(className, String.format("Problem with malformed URL. Requested url=%s", url));
        } catch (IOException ioexception) {
            Log.e(className, String.format("Problem with access to data. Requested url=%s", url));
        }

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
