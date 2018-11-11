package com.goteam.gohomerepairservicesapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private LinkedList<Service> userList;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String TAG= "AdminActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new ServiceAdapter(getSerivces());
        userList=new LinkedList<Service>();
    }
    public void btnLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private boolean serviceSimpleVerification(String name, double rate) {
        if (name.isEmpty()) {
            Toast.makeText(AdminActivity.this, "Service must have a  valid name. Please try again.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (rate < 0) {
            Toast.makeText(AdminActivity.this, "Service must have a valid rate. Please try again.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }



    private void addService(String name, double rate){
        Service service=new Service(name,rate);
        if(serviceSimpleVerification(name,rate)){
            database.getReference("Services").child(service.getServiceName()).setValue(service).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        Log.e(TAG, "Failed to save user info", task.getException());
                        Toast.makeText(AdminActivity.this, "Failed to create new service. Please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Toast.makeText(AdminActivity.this, "You have successfully created a new service.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    public void  btnAddServiceClicked(View view){
        AlertDialog.Builder inputDialog= new AlertDialog.Builder(this);


        inputDialog.setTitle("New Service");

        inputDialog.setView(R.layout.service_text_entry);

        inputDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Dialog dialog= (Dialog) dialogInterface;
                EditText serviceInput=dialog.findViewById(R.id.serviceRegister);
                EditText rateInput=dialog.findViewById(R.id.rateRegister);
                String serviceName=serviceInput.getText().toString();
                Double rate=Double.valueOf(rateInput.getText().toString());
                System.out.print("test");

                addService(serviceName,rate);
            }
        });

        inputDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        inputDialog.show();

    }

    public LinkedList<Service> getSerivces(){
        database.getReference("Services").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String serviceName = (String)snapshot.child("serviceName").getValue();
                    Long serviceRate = (Long) snapshot.child("rate").getValue();
                    userList.add(new Service(serviceName,serviceRate));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return userList;

    }
}
