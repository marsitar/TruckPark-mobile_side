package com.example.truckpark.domain.json.weatherapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cloud {

    @JsonProperty("all")
    private String all;

    public Cloud() {
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }
}
