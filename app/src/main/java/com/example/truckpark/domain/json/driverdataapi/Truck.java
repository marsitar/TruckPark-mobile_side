package com.example.truckpark.domain.json.driverdataapi;

public class Truck {

    private Long id;

    private String registration;

    private String brand;

    private String model;

    private Integer carYear;

    private TruckDriverWay truckDriverWay;

    private Company company;

    public Truck() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getCarYear() {
        return carYear;
    }

    public void setCarYear(Integer carYear) {
        this.carYear = carYear;
    }

    public TruckDriverWay getTruckDriverWay() {
        return truckDriverWay;
    }

    public void setTruckDriverWay(TruckDriverWay truckDriverWay) {
        this.truckDriverWay = truckDriverWay;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
