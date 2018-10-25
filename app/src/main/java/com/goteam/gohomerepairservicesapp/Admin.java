package com.goteam.gohomerepairservicesapp;

public class Admin extends User {

    @SuppressWarnings("unused") // for Firebase usage
    public Admin() { }

    public Admin(String uid){
        super(uid);
    }

    @Override
    public String getRoleName() {
        return "Admin";
    }

}
