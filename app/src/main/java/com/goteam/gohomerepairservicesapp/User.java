package com.goteam.gohomerepairservicesapp;

public abstract class User {
    protected String role;

    @SuppressWarnings("unused") // for Firebase usage
    public User() { }

    public User(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public abstract String getRoleName();
}
