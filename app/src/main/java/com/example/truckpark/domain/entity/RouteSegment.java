package com.example.truckpark.domain.entity;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class RouteSegment {

    private Duration duration;
    private Integer distance;

    @Builder.Default
    private Double[][] points = new Double[2][2];

    @Builder.Default
    private List<Double[]> innerPoints = new ArrayList<>();

}
