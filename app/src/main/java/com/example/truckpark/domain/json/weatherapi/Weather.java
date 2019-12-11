package com.example.truckpark.domain.json.weatherapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

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

    public Weather() {
    }

    public List<Phenomenon> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<Phenomenon> weathers) {
        this.weathers = weathers;
    }

    public MainWeatherData getMainWeatherData() {
        return mainWeatherData;
    }

    public void setMainWeatherData(MainWeatherData mainWeatherData) {
        this.mainWeatherData = mainWeatherData;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
