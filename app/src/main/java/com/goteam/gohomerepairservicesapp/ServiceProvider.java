package com.goteam.gohomerepairservicesapp;


import java.util.LinkedList;

public class ServiceProvider extends User {

    private String address, phoneNumber, companyName, description;
    private boolean licenced;
    private boolean account_finalized;
    private LinkedList<Service> services;


    @SuppressWarnings("unused") // for Firebase usage
    public ServiceProvider() {
        super("provider");
        account_finalized=false;

    }

    public ServiceProvider(boolean licensed) {
        super("provider");
        account_finalized=false;
        this.licenced = false;
    }

    public ServiceProvider(String address) {
        super("provider");
        account_finalized=false;
        this.address = address;
    }

    public void addService(Service service){
        services.add(service);
    }

    public void removeService(Service service){
        services.remove(service);
    }

    public LinkedList<Service> getServices(){
        return services;
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
