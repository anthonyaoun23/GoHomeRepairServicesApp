package com.goteam.gohomerepairservicesapp;

public abstract class User {
    protected String uid;

    @SuppressWarnings("unused") // for Firebase usage
    public User() { }

    public User(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public abstract String getRoleName();
}
