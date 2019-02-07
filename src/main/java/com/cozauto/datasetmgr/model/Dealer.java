package com.cozauto.datasetmgr.model;

public class Dealer {

    private long dealerId;
    private String name;
    private Vehicle[] vehicle;

    public long getDealerId() {
        return dealerId;
    }

    public void setDealerId(long dealerId) {
        this.dealerId = dealerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vehicle[] getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle[] vehicle) {
        this.vehicle = vehicle;
    }
}
