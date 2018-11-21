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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceProviderActivity extends AppCompatActivity {

    private RecyclerView availableServices_r;
    private RecyclerView currentServices_r;
    private SpAvailableServiceAdapter availableServiceAdapter;
    private SpCurrentServiceAdapter currentServiceAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Service> currentServices;
    private ArrayList<Service> availableServices;
    private ServiceProvider serviceProvider;
    private FirebaseUser firebaseUser;
    private String uid;



    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String TAG = "ServiceProviderActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_new);
        //Load user
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        uid=firebaseUser.getUid();

        database.getReference().child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceProvider = dataSnapshot.child(uid).getValue(ServiceProvider.class);

                GenericTypeIndicator<List<Service>> gen = new GenericTypeIndicator<List<Service>>() {};
                List<Service> current = dataSnapshot.child(uid).getValue(gen);

                currentServices= (ArrayList<Service>)current;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

      //  serviceProvider.loadServices();

        loadRecyclers();

    }


    private void loadRecyclers(){
        availableServices = new ArrayList<Service>();
        currentServices = new ArrayList<Service>();
        loadAvailableServices();
        availableServices_r = findViewById(R.id.availableServices);
        availableServices_r.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        availableServices_r.setLayoutManager(layoutManager);
        availableServiceAdapter = new SpAvailableServiceAdapter(availableServices);
        availableServices_r.setAdapter(availableServiceAdapter);

        setupRecyclers();


    }

    public void loadAvailableServices() {
        database.getReference("Services").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String serviceName = (String) snapshot.child("serviceName").getValue();
                    Long serviceRate = (Long) snapshot.child("rate").getValue();
                    availableServices.add(new Service(serviceName, serviceRate));
                    availableServiceAdapter.notifyDataSetChanged();
                }

                availableServiceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setupRecyclers(){
        availableServiceAdapter.setOnCardClick(new SpAvailableServiceAdapter.OnItemClickListener() {
            @Override
            public void onAddClick(int position) {
                serviceProvider.addService(availableServices.get(position));
                currentServices.remove(position);
                currentServiceAdapter.notifyDataSetChanged();
                availableServiceAdapter.notifyDataSetChanged();
                database.getReference().child("User").child(uid).setValue(serviceProvider);
            }
        });
    }

    public void btnLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
