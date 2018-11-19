package com.goteam.gohomerepairservicesapp;


public class ServiceProvider extends User {

    private String address, phoneNumber, companyName, description;
    private boolean licenced;
    private boolean account_finalized;


    @SuppressWarnings("unused") // for Firebase usage
    public ServiceProvider() {
        super("provider");
        account_finalized=false;

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
    public boolean getAccount_finalized(){return account_finalized;}

}
