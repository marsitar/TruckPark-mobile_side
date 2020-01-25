package com.example.truckpark.domain.entity;

import java.time.Duration;

public class RouteSegment {

    private Duration duration;
    private Integer distance;
    private Double[][] points = new Double[2][2];

    public static final class Builder {

        private Duration duration;
        private Integer distance;
        private Double[][] points = new Double[2][2];

        public Builder withDuration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public Builder withDistance(Integer distance) {
            this.distance = distance;
            return this;
        }

        public Builder withPoints(Double[][] points) {
            this.points = points;
            return this;
        }

        public RouteSegment build() {

            RouteSegment routeSegment = new RouteSegment();

            routeSegment.duration = this.duration;
            routeSegment.distance = this.distance;
            routeSegment.points = this.points;

            return routeSegment;
        }
    }

    public Duration getDuration() {
        return duration;
    }

    public Integer getDistance() {
        return distance;
    }

    public Double[][] getPoints() {
        return points;
    }
}
