package com.goteam.gohomerepairservicesapp;

public class Account {
    protected String name;
    protected String email;
    protected String password;
    protected String type; //This is here so the compiler dosent complain when trying to pull type from firebase


    public Account(){}


    public Account(String name, String email, String password){
        this.email=email;
        this.name=name;
        this.password=password;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public void setEmail(){
        this.email=email;
    }
}
