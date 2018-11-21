package com.goteam.gohomerepairservicesapp;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ServiceProvider extends User {

    private String address, phoneNumber, companyName, description;
    private boolean licenced;
    private boolean accountFinalized;
    private ArrayList<Service> services;
    private ArrayList<TimeOfAvailability> availabilities;


    public ServiceProvider() {
        super("provider");
        accountFinalized = false;
        services = new ArrayList<>();
        availabilities = new ArrayList<>();
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addService(Service service) {
        services.add(service);
    }

    public void removeService(Service service) {
        services.remove(service);
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    @Override
    public String getRoleName() {
        return "Service Provider";
    }


    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDescription() {
        return description;
    }

    public boolean getLicensed() {
        return licenced;
    }

    public void setLicenced(boolean licenced) {
        this.licenced = licenced;
    }

    public boolean getAccountFinalized() {
        return accountFinalized;
    }

}
