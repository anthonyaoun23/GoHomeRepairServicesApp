package com.goteam.gohomerepairservicesapp;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class Admin extends User  {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public Admin() {
        super("admin");
    }

    @Override
    public String getRoleName() {
        return "Admin";
    }
    


}
