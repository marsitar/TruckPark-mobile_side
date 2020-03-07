package com.example.truckpark.domain.entity;

import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MopForm {

    private Duration leftTime;
    private int leftKilometers;
    private String mopName;
    private int freePlacesForTrucks;

}
