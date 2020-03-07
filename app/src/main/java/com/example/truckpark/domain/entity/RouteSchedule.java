package com.example.truckpark.domain.entity;

import java.time.LocalDateTime;
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
public class RouteSchedule {

    private LocalDateTime saveDateAndTime;

    @Builder.Default
    private List<RoutePart> routeParts = new ArrayList<>();

}
