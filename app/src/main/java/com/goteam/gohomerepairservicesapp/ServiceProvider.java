package com.goteam.gohomerepairservicesapp;


public class ServiceProvider extends User {

    @SuppressWarnings("unused") // for Firebase usage
    public ServiceProvider() { }

    public ServiceProvider(String uid){
        super(uid);
    }

    @Override
    public String getRoleName() {
        return "Service Provider";
    }
}
