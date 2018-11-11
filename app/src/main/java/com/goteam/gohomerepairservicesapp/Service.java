package com.goteam.gohomerepairservicesapp;



public class Service {

    private String name;
    private double hourlyRate;

    public Service(){} //For firebase

    public Service(String name, double hourlyRate){
        this.hourlyRate=hourlyRate;
        this.name=name;
    }

    public void setRate(double rate){
        hourlyRate=rate;
    }

    public String getServiceName() {return name;}

    public double getRate() {return hourlyRate;}
}


