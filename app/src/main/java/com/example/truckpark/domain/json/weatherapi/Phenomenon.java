package com.example.truckpark.domain.json.weatherapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(value = {"id", "description", "icon"})
public class Phenomenon {

    @JsonProperty("main")
    private String main;

    public Phenomenon() {
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
