package com.goteam.gohomerepairservicesapp;


public class Service {

    private String name;
    private double hourlyRate;

    public Service() {
    } //For firebase

    public Service(String name, double hourlyRate) {
        this.hourlyRate = hourlyRate;
        this.name = name;
    }

    public double getRate() {
        return hourlyRate;
    }

    public void setRate(double rate) {
        hourlyRate = rate;
    }

    public String getServiceName() {
        return name;
    }

    public void setServiceName(String name) {
        this.name = name;
    }
}


