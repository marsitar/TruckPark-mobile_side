package com.example.truckpark.service.mopdata;

import android.content.Context;

import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.properties.PropertyManager;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class RequestMopDataService {

    private final String URI;
    private final String CATEGORY;

    public RequestMopDataService(Context context) {
        PropertyManager propertyManager = new PropertyManager("truckparkserver.properties");
        URI = propertyManager.getProperty("URI", context);
        CATEGORY = "mops";
    }

    public List<Mop> getAllMopsData() {
        ObjectMapper mapperJsonToClass = new ObjectMapper();
        String url = buildUrl(CATEGORY);
        List<Mop> mopsData = null;
        try {
            Mop[] mopsArrayData = mapperJsonToClass.readValue(new URL(url), Mop[].class);
            mopsData = Arrays.asList(mopsArrayData);
            String test = null;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mopsData;
    }

    public Mop getMopById(String id) {

        ObjectMapper mapperJsonToClass = new ObjectMapper();
        String url = buildUrl(CATEGORY, id);
        Mop mopData = null;

        try {
            mopData = mapperJsonToClass.readValue(new URL(url), Mop.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mopData;
    }

    private String buildUrl(String category) {

        StringBuilder buildedURL = new StringBuilder();

        buildedURL.append(URI);
        buildedURL.append("/");
        buildedURL.append(category);
        buildedURL.append("/");
        buildedURL.append("all");

        return buildedURL.toString();
    }

    private String buildUrl(String category, String id) {

        StringBuilder buildedURL = new StringBuilder();

        buildedURL.append(URI);
        buildedURL.append("/");
        buildedURL.append(category);
        buildedURL.append("/");
        buildedURL.append("mop");
        buildedURL.append("/");
        buildedURL.append(id);

        return buildedURL.toString();
    }
}
