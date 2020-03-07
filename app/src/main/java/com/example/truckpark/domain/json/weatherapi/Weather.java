package com.example.truckpark.domain.json.weatherapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"coord", "base", "dt", "sys", "timezone", "id", "cod"})
public class Weather {

    @JsonProperty("weather")
    private List<Phenomenon> weathers = new ArrayList<>();

    @JsonProperty("main")
    private MainWeatherData mainWeatherData;

    @JsonProperty("visibility")
    private Integer visibility;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("clouds")
    private Cloud cloud;

    @JsonProperty("name")
    private String name;

}
