package com.goteam.gohomerepairservicesapp;

import java.io.Serializable;

public abstract class User implements Serializable {
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
