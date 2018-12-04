package com.goteam.gohomerepairservicesapp;



public class Service {

    private String serviceName;
    private double hourlyRate;

    public Service(){} //For firebase

    public Service(String name, double hourlyRate){
        this.hourlyRate=hourlyRate;
        this.serviceName=name;
    }

    public void setRate(double rate){
        hourlyRate=rate;
    }

    public String getServiceName() {return serviceName;}

    public double getRate() {return hourlyRate;}
}


