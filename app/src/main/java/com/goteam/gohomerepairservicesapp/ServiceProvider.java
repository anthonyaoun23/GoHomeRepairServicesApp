package com.goteam.gohomerepairservicesapp;


public class ServiceProvider extends User {

    private String address, phoneNumber, companyName, description;
    private boolean licenced;


    @SuppressWarnings("unused") // for Firebase usage
    public ServiceProvider() {
        super("provider");

    }

    @Override
    public String getRoleName() {
        return "Service Provider";
    }


    public String getAddress(){return address;}
    public String getPhoneNumber(){return phoneNumber;}
    public String getCompanyName(){return companyName;}
    public String getDescription(){return description;}
    public boolean getLicensed(){return licenced;}

}
