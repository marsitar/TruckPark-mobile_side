package com.example.truckpark.domain.entity;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(access = AccessLevel.PUBLIC)
@Getter
@ToString
@EqualsAndHashCode
public class RoutePart {

    private Duration duration;
    private Integer distance;
    private String origin;
    private String destination;

    @Builder.Default
    private List<RouteSegment> routeSegments = new ArrayList<>();

}
