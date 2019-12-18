package com.example.truckpark.domain.json.driverdataapi;

import java.util.ArrayList;
import java.util.List;

public class Driver {

    private Long id;

    private String fullName;

    private Company company;

    private List<TruckDriverWay> truckDriverWaysDTO = new ArrayList<>();

    public Driver() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<TruckDriverWay> getTruckDriverWaysDTO() {
        return truckDriverWaysDTO;
    }

    public void setTruckDriverWaysDTO(List<TruckDriverWay> truckDriverWaysDTO) {
        this.truckDriverWaysDTO = truckDriverWaysDTO;
    }
}
