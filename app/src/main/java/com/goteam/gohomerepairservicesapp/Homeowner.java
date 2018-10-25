package com.goteam.gohomerepairservicesapp;

public class Homeowner extends User {

    @SuppressWarnings("unused") // for Firebase usage
    public Homeowner() {
        super("homeowner");
    }

    @Override
    public String getRoleName() {
        return "Homeowner";
    }

}
