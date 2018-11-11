package com.goteam.gohomerepairservicesapp;

public class Admin extends User  {
    public Admin() {
        super("admin");
    }

    @Override
    public String getRoleName() {
        return "Admin";
    }
}
