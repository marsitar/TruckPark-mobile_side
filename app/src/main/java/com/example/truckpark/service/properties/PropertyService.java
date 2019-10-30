package com.example.truckpark.service.properties;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class PropertyService {
    private final String propertyFileName;
    private final Properties properties;

    public PropertyService(String propertyFileName) {
        this.propertyFileName=propertyFileName;
        properties = new Properties();
    }

    {
        prepareProperties();
    }

    private void prepareProperties() {
        try {
            properties.load(Objects.requireNonNull(Thread.currentThread()
                    .getContextClassLoader().getResource(propertyFileName))
                    .openStream());
        } catch (IOException e) {

        }
    }

    public String getProperty(String property) {
        return properties.getProperty(property);
    }
}
