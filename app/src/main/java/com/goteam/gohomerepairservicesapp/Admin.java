package com.goteam.gohomerepairservicesapp;

public class Admin extends User {

    @SuppressWarnings("unused") // for Firebase usage
    public Admin() {
        super("admin");
    }

    @Override
    public String getRoleName() {
        return "Admin";
    }


}
