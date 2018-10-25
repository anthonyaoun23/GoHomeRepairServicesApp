package com.goteam.gohomerepairservicesapp;

public class User {

    public String name, email, accountType;

    public User(){

    }

    public User(String name, String email, String accountType){
        this.name = name;
        this.email = email;
        this.accountType = accountType;

    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAccountType() {
        return accountType;
    }
}
