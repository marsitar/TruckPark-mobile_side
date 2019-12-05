package com.example.truckpark.domain.json.weatherapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(value = {"coord", "weather", "base", "main", "visibility", "wind", "clouds", "dt", "sys", "id", "name", "cod"})
public class Weather {

    @JsonProperty("weather")
    private Set<Phenomenon> weathers = new HashSet<>();

    @JsonProperty("main")
    private MainWeatherData mainWeatherData;

    @JsonProperty("visibility")
    private Integer visibility;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("clauds")
    private Cloud cloud;

    public Weather() {
    }

    public Set<Phenomenon> getWeathers() {
        return weathers;
    }

    public void setWeathers(Set<Phenomenon> weathers) {
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
}
