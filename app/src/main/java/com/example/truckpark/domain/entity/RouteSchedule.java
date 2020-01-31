package com.example.truckpark.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RouteSchedule {

    private LocalDateTime saveDateAndTime;
    private List<RoutePart> routeParts = new ArrayList<>();

    public static final class Builder {

        private LocalDateTime saveDateAndTime;
        private List<RoutePart> routeParts = new ArrayList<>();

        public Builder withSaveDateAndTime(LocalDateTime saveDateAndTime) {
            this.saveDateAndTime = saveDateAndTime;
            return this;
        }

        public Builder withRouteParts(List<RoutePart> routeParts) {
            this.routeParts = routeParts;
            return this;
        }

        public RouteSchedule build() {

            RouteSchedule routeSchedule = new RouteSchedule();

            routeSchedule.saveDateAndTime = this.saveDateAndTime;
            routeSchedule.routeParts = this.routeParts;

            return routeSchedule;
        }
    }

    public LocalDateTime getSaveDateAndTime() {
        return saveDateAndTime;
    }

    public List<RoutePart> getRouteParts() {
        return routeParts;
    }
}
