package com.example.truckpark.service.properties;

import android.content.Context;
import android.content.res.AssetManager;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyService {
    private final String propertyFileName;
    private final Properties properties;

    public PropertyService(String propertyFileName) {
        this.propertyFileName=propertyFileName;
        this.properties = new Properties();
    }

    public String getProperty(String propertyKey, Context context) {

        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(propertyFileName);
            properties.load(inputStream);
        } catch (IOException e) {

        }
        return properties.getProperty(propertyKey);
    }
}
