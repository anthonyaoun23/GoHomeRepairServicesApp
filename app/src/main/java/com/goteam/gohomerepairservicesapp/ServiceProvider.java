package com.goteam.gohomerepairservicesapp;


public class ServiceProvider extends User {

    @SuppressWarnings("unused") // for Firebase usage
    public ServiceProvider() {
        super("provider");
    }

    @Override
    public String getRoleName() {
        return "Service Provider";
    }
}
