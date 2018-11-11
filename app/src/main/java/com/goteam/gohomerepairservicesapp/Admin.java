package com.goteam.gohomerepairservicesapp;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class Admin extends User  {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private LinkedList<Service> userList;

    public Admin() {
        super("admin");
    }

    @Override
    public String getRoleName() {
        return "Admin";
    }
    
    public LinkedList<Service> getServices(){
        database.getReference("Services").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    userList.add(snapshot.getValue(Service.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return userList;
    }
}
