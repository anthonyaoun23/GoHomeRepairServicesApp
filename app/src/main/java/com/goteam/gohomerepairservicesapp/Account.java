package com.goteam.gohomerepairservicesapp;

public class Account {
    protected String name;
    protected String email;
    protected String password;


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
