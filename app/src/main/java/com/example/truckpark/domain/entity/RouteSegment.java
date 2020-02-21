package com.example.truckpark.domain.entity;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RouteSegment {

    private Duration duration;
    private Integer distance;
    private Double[][] points = new Double[2][2];
    private List<Double[]> innerPoints = new ArrayList<>();

    public static final class Builder {

        private Duration duration;
        private Integer distance;
        private Double[][] points = new Double[2][2];
        private List<Double[]> innerPoints = new ArrayList<>();

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

        public Builder withInnerPoints(List<Double[]> innerPoints) {
            this.innerPoints = innerPoints;
            return this;
        }

        public RouteSegment build() {

            RouteSegment routeSegment = new RouteSegment();

            routeSegment.duration = this.duration;
            routeSegment.distance = this.distance;
            routeSegment.points = this.points;
            routeSegment.innerPoints = this.innerPoints;

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

    public List<Double[]> getInnerPoints() {
        return innerPoints;
    }
}
