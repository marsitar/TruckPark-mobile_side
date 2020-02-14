package com.example.truckpark.domain.json.mopapi;

import com.example.truckpark.domain.json.positionapi.Coordinate;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Mop {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("identificationName")
    private String identificationName;

    @JsonProperty("category")
    private String category;

    @JsonProperty("place")
    private String place;

    @JsonProperty("coordinateDTO")
    private Coordinate coordinate;

    @JsonProperty("roadNumber")
    private String roadNumber;

    @JsonProperty("truckPlaces")
    private Integer truckPlaces;

    @JsonProperty("occupiedTruckPlaces")
    private Integer occupiedTruckPlaces;

    @JsonProperty("extendedId")
    private String extendedId;

    @JsonProperty("extendedMopDataDTO")
    private ExtendedMopData extendedMopData;

    public Mop() {
    }

    public Mop(Long id, String identificationName, String category, String place, Coordinate coordinate, String roadNumber, Integer truckPlaces, Integer occupiedTruckPlaces, String extendedId, ExtendedMopData extendedMopData) {
        this.id = id;
        this.identificationName = identificationName;
        this.category = category;
        this.place = place;
        this.coordinate = coordinate;
        this.roadNumber = roadNumber;
        this.truckPlaces = truckPlaces;
        this.occupiedTruckPlaces = occupiedTruckPlaces;
        this.extendedId = extendedId;
        this.extendedMopData = extendedMopData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificationName() {
        return identificationName;
    }

    public void setIdentificationName(String identificationName) {
        this.identificationName = identificationName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    public Integer getTruckPlaces() {
        return truckPlaces;
    }

    public void setTruckPlaces(Integer truckPlaces) {
        this.truckPlaces = truckPlaces;
    }

    public Integer getOccupiedTruckPlaces() {
        return occupiedTruckPlaces;
    }

    public void setOccupiedTruckPlaces(Integer occupiedTruckPlaces) {
        this.occupiedTruckPlaces = occupiedTruckPlaces;
    }

    public String getExtendedId() {
        return extendedId;
    }

    public void setExtendedId(String extendedId) {
        this.extendedId = extendedId;
    }

    public ExtendedMopData getExtendedMopData() {
        return extendedMopData;
    }

    public void setExtendedMopData(ExtendedMopData extendedMopData) {
        this.extendedMopData = extendedMopData;
    }

    @Override
    public String toString() {
        return "MopForm{" +
                "id=" + id +
                ", identificationName='" + identificationName + '\'' +
                ", category='" + category + '\'' +
                ", place='" + place + '\'' +
                ", coordinate=" + coordinate +
                ", roadNumber='" + roadNumber + '\'' +
                ", truckPlaces=" + truckPlaces +
                ", occupiedTruckPlaces='" + occupiedTruckPlaces + '\'' +
                ", extendedId='" + extendedId + '\'' +
                ", extendedMopData=" + extendedMopData +
                '}';
    }
}
