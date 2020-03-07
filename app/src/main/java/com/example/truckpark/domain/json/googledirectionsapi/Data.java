package com.example.truckpark.domain.json.googledirectionsapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Data {

    @JsonProperty("text")
    private String text;

    @JsonProperty("value")
    private Integer value;

}
