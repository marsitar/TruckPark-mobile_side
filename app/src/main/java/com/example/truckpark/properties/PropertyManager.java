package com.example.truckpark.properties;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {

    private final String propertyFileName;
    private final Properties properties;
    private String className = this.getClass().getSimpleName();

    public PropertyManager(String propertyFileName) {
        this.propertyFileName = propertyFileName;
        this.properties = new Properties();
    }

    public String getProperty(String propertyKey, Context context) {

        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(propertyFileName);
            properties.load(inputStream);
        } catch (IOException e) {
            Log.e(className, "Problem with access to data.");
        }
        return properties.getProperty(propertyKey);
    }
}
