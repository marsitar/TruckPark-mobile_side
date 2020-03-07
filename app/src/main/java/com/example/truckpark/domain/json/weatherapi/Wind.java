package com.example.truckpark.domain.json.weatherapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wind {

    @JsonProperty("speed")
    private Integer speed;

    @JsonProperty("deg")
    private Double deg;

}
