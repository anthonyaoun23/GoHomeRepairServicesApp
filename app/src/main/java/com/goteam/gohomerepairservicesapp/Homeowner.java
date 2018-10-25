package com.goteam.gohomerepairservicesapp;

public class Homeowner extends User {

    @SuppressWarnings("unused") // for Firebase usage
    public Homeowner() { }

    public Homeowner(String uid){
        super(uid);
    }

    @Override
    public String getRoleName() {
        return "Homeowner";
    }

}
