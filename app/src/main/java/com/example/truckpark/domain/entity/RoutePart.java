package com.example.truckpark.domain.entity;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RoutePart {

    private Duration duration;
    private Integer distance;
    private String origin;
    private String destination;
    private List<RouteSegment> routeSegments = new ArrayList<>();

    public static final class Builder {

        private Duration duration;
        private Integer distance;
        private String origin;
        private String destination;

        private List<RouteSegment> routeSegments = new ArrayList<>();

        public Builder withDuration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public Builder withDistance(Integer distance) {
            this.distance = distance;
            return this;
        }

        public Builder withOrigin(String origin) {
            this.origin = origin;
            return this;
        }

        public Builder withDestination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder withRouteSegments(List<RouteSegment> routeSegments) {
            this.routeSegments = routeSegments;
            return this;
        }

        public RoutePart build() {

            RoutePart routePart = new RoutePart();

            routePart.duration = this.duration;
            routePart.distance = this.distance;
            routePart.origin = this.origin;
            routePart.destination = this.destination;
            routePart.routeSegments = this.routeSegments;

            return routePart;
        }
    }

    public Duration getDuration() {
        return duration;
    }

    public Integer getDistance() {
        return distance;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public List<RouteSegment> getRouteSegments() {
        return routeSegments;
    }
}
