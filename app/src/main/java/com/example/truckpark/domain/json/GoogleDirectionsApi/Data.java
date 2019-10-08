package com.example.truckpark.domain.json.GoogleDirectionsApi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

    @JsonProperty("text")
    private String text;

    @JsonProperty("value")
    private Integer value;

    public Data() {
    }

    public Data(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
